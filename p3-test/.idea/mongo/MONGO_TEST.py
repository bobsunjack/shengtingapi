import pymongo

myclient=pymongo.MongoClient("mongodb://192.168.0.104:27017")

mydb=myclient["capturedb"]
collist=mydb.list_collection_names()

for x in collist :
    print(x)


