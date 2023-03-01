import re
from datetime import datetime
import sys
from bson.objectid import ObjectId
import pymongo
import requests
import time




def log(msg):
    now = datetime.now();
    dt = now.strftime("%Y-%m-%d %H:%M:%S.%f")[0:-3]
    print(dt + " " + msg, flush=True)

def position_to_db( position ):
    x = stPositions.find_one( {"$and" : [ {"time": {"$regex": position["time"] }}, {"MMSI": position["MMSI"] } ]} )
    if x == None:
        z = stPositions.update_one({"time": position["time"]}, {"$set": position}, upsert=True)
        log("Logging new position MMSI: {} position (long/lat) {} / {} @ {}".format(position["MMSI"], position["long"], position["lat"], position["time"] ))
    else:
        log("Latest position reported MMSI: {} position (long/lat) {} / {} @ {}".format(position["MMSI"], position["long"], position["lat"], position["time"] ))

def connectToMongo():
    tclient = pymongo.MongoClient("mongodb://192.168.42.11:27017/")
    try:
        tclient.admin.command('ismaster')
    except Exception as e:
        log("Failed to connect to DB, reason: " + str(e.msg))
        sys.exit(0)

    stDB = tclient["sailtracker"]
    mtDB= tclient["matilda"]
    global stPositions
    global mtPositions


    stPositions = stDB["Position"]
    mtPositions = mtDB["Position"]

    for pos in mtPositions.find():
        position = dict()
        position["lat"] = pos["lat"]
        position["long"] = pos["long"]
        position["source"] = "merge"
        position["MMSI"] = "265633260"
        position["time"] = pos["time"].replace("  ", " ")
        now = datetime.now()
        position["reportTime"] = now.strftime("%Y-%m-%d %H:%M:%S")
        position_to_db( position )

def main():
    connectToMongo()





if __name__ == '__main__':
    main()