from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
import re
from datetime import datetime
import sys
from bson.objectid import ObjectId
import pymongo
import requests
import time



# Mongo DB parameters
param_db_host = "192.168.42.11"
param_db_name= "sailtracker"
param_db_port = 27017

# selenium scraping
driver = None


def log(msg):
    now = datetime.now()
    dt = now.strftime("%Y-%m-%d %H:%M:%S.%f")[0:-3]
    print(dt + " " + msg, flush=True)

def connectToMongo():
    global param_db_host
    global param_db_port
    global param_db_name


    mclient = pymongo.MongoClient("mongodb://" + param_db_host + ":" + str(param_db_port) + "/")
    try:
        mclient.admin.command('ismaster')
    except Exception as e:
        log("Failed to connect to DB, reason: " + str(e.msg))
        sys.exit(0)

    mdb = mclient[ param_db_name ]
    global colUsers
    global colPositions


    colPositions = mdb["Position"]
    colUsers = mdb["User"]
    log("Successfully connected to " + "mongodb://" + param_db_host + ":" + str(param_db_port) + "/")


def get_driver():
    options = webdriver.ChromeOptions()
    options.add_argument("--headless")
    options.add_argument("--disable-gpu")
    return webdriver.Chrome(
        service=ChromeService(ChromeDriverManager().install()),
        options=options,
    )

def find_shipid( mmsi ) -> int:
    url = "https://www.marinetraffic.com/en/ais/details/ships/mmsi:" + mmsi
    try:
        driver.get( url )
        element=driver.find_element(By.XPATH,"//meta[@property='og:url']")
        content = element.get_attribute("content")
        result = re.search(r"shipid:(\d+)", content)
        return result.group(1)
    except Exception as e:
        log( "failed to scrap ship identity, reason: " + e.msg )
        sys.exit(0)

def update_missing_shipids():
    for usr in colUsers.find():
        if (usr["shipId"] == 0):
            usr["shipId"] = find_shipid( usr["MMSI"])
            print( str(usr["_id"]) )
            result = colUsers.update_one({"_id": ObjectId(str(usr["_id"]))}, {"$set": {"shipId": usr["shipId"]}})
            if ( usr["shipId"] != 0):
                log("User shipId updated MMSI: " + usr["MMSI"] + " shipId: " + usr["shipId"])
            else:
                log("User shipId updated MMSI: " + usr["MMSI"] + " is not updated ")

def position_to_db( position ):
    x = colPositions.find_one( {"$and" : [ {"time": {"$regex": position["time"] }}, {"MMSI": position["MMSI"] } ]} )
    if x == None:
        z = colPositions.update_one({"time": position["time"]}, {"$set": position}, upsert=True)
        log("Logging new position MMSI: {} position (long/lat) {} / {} @ {}".format(position["MMSI"], position["long"], position["lat"], position["time"] ))
    else:
        log("Latest position reported MMSI: {} position (long/lat) {} / {} @ {}".format(position["MMSI"], position["long"], position["lat"], position["time"] ))

def get_position( mmsi, ship_id):
    url = "https://www.marinetraffic.com/vesselDetails/latestPosition/shipid:{}".format(str(ship_id))
    headers = {
        "accept": "application/json",
        "accept-encoding": "gzip, deflate",
        "user-agent": "Mozilla/5.0",
        "x-requested-with": "XMLHttpRequest"
    }

    try:
        response = requests.get(url, headers=headers)
        response.raise_for_status()
    except Exception as e:
        log("failed to connect to " + url )
        sys.exit(0)

    # Build position record
    data = response.json()
    position = dict()
    position["lat"] = data["lat"]
    position["long"] = data["lon"]
    position["source"] = "marinetraffic"
    position["MMSI"] = mmsi
    position["time"] = time.strftime("%Y-%m-%d %H:%M", time.gmtime( int( data["lastPos"])))
    now = datetime.now()
    position["reportTime"] = now.strftime("%Y-%m-%d %H:%M:%S")

    # new position to database
    position_to_db( position )
def collect_positions():
    for usr in colUsers.find():
        if (usr["shipId"] != 0):
            now = datetime.now()
            if ((now.hour % usr["collectFrequencyHH"]) == 0):
                get_position( usr["MMSI"], str(usr["shipId"]) )

def main():
    global driver
    driver = get_driver()
    connectToMongo()
    update_missing_shipids()
    collect_positions()

if __name__ == '__main__':
    main()