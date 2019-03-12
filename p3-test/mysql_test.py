import mysql.connector

mydb = mysql.connector.connect(host="localhost", user="root", passwd="root",database="basic")

print(mydb)

mycursor=mydb.cursor()
mycursor.execute("show databases")
for x in mycursor :
    print(x)

mycursor.execute("select loginName,name from user")

for x in mycursor:
    (loginame,name)=x
    print(loginame)