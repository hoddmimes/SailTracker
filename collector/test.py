from datetime import datetime

def main():

    for x in range(0,40):
        if ((x % 4) == 0):
            now = datetime.now()
            print(str(x) + " hour: " + str(now.hour))




if __name__ == '__main__':
    main()