import socket
import sys

serversocket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

host=socket.gethostname()
port=9999

serversocket.bind((host,port))

serversocket.listen(5)

while True:
    clientSocket,addr=serversocket.accept()
    print("连接地址%s",str(addr))
    clientSocket.send("测试数据".encode("utf-8"))
    clientSocket.close()
