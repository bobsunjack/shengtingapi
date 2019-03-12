import tkinter
from tkinter import *
import tkinter.messagebox
import argparse
import socket
import sys
import os
import re
import threading
import datetime
#coding =utf - 8

class ItemTask:
    def setBtn(self,btn):
        self.btn=btn;

    def __init__(self,name,port,path,script,auto,dayTime=None):
        self.name=name
        self.port=port
        self.path=path
        self.script=script
        self.auto=auto
        self.dayTime=dayTime
def scanPort(tasks):
    for task in tasks:
        scan_ports(task)
    timer = threading.Timer(7, scanPort,[tasks])
    timer.start()



def scan_ports(task,isFirst=False):
    """Scan remote hosts"""
    start_port =int(task.port)
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    except  Exception as e :
        print("Socket creation failed.Error code:" + str(e.args) + "Erroe message:" + e.args)
        sys.exit(True);
    try:
        remote_ip = socket.gethostbyname("localhost")
    except  Exception as e:
        print(e)
        sys.exit()

    try:
        sock.connect((remote_ip,start_port))
        print ('Port' + str(start_port) + 'is ipen')
        sock.close()
        task.btn.configure(bg="green", command=lambda: kill_process(task))
        "  task.btn.config(state=DISABLED)"
        "sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)"
    except  Exception as e:
        if isFirst:
            start_process(task);
        else:
            if task.auto:
                 start_process(task)
            print(str(e)+"port:"+str(start_port))
            task.btn.configure(bg="gray", command=lambda: start_process(task))
            pass

def callback():
    tkinter.messagebox.showinfo("Python command","人生苦短、我用Python")

def start_process(task):
    status = os.system("start /D \""+task.path+"\" "+task.script)
    task.btn.configure(bg="green", command=lambda: kill_process(task))
    print(status)
def kill_process(task):
    port=task.port
    regex = re.compile('\s+')
    ret = os.popen("netstat -aon | findstr "+str(port))
    str_list = ret.read()
    ret_list = regex.split(str_list)
    process_pid = ret_list[5]
    task.btn.configure(bg="gray", command=lambda: start_process(task))
    os.system("taskkill /pid "+process_pid+" -t -f ")

##03:00:00 daytime
def intervalTime(dayTime):
    # 获取现在时间
    now_time = datetime.datetime.now()
    # 获取明天时间
    next_time = now_time + datetime.timedelta(days=+1)
    next_year = next_time.date().year
    next_month = next_time.date().month
    next_day = next_time.date().day
    # 获取明天3点时间
    next_time = datetime.datetime.strptime(str(next_year)+"-"+str(next_month)+"-"+str(next_day)+" "+dayTime, "%Y-%m-%d %H:%M:%S")
    # # 获取昨天时间
    # last_time = now_time + datetime.timedelta(days=-1)

    # 获取距离明天3点时间，单位为秒
    timer_start_time = (next_time - now_time).total_seconds()
    if timer_start_time>86400:
        timer_start_time=timer_start_time-86400
    print(timer_start_time)
    return timer_start_time

def stopDayService(start_time,task,isFirst=True):
    if isFirst:
        timer = threading.Timer(start_time, stopDayService,(86400,task,False))
        timer.start()
    else:
        kill_process(task)
        timer = threading.Timer(86400, stopDayService,(86400,task,False))
        timer.start()

def initDayService(tasks):
    for task in tasks:
        if task.dayTime != None:
          next_time=intervalTime(task.dayTime)
          stopDayService(next_time,task)




tasks=[];

with open('b.txt') as file_object:
    regex = re.compile('###')
    for line in file_object.readlines():
        items=regex.split(line);
        if len(items) == 5:
            items[4]=False;
        else:
            items.append(True);
        tasks.append(ItemTask(items[0],items[1],items[2],items[3],items[4],items[5]))

root = tkinter.Tk()
for task in tasks:
    btn = Button(root, text=task.name, fg="blue",bd=2,width=28,command= lambda: kill_process(task))
    btn.pack();
    task.setBtn(btn);
    scan_ports(task,True);

initDayService(tasks)

#scanPort(tasks,True)
timer = threading.Timer(3, scanPort,[tasks])
timer.start()
# 进入消息循环
root.mainloop()
"""
btn2 = Button(root, text="设置command事件调用命令", fg="green",bd=2,width=28,command=callback)
btn2.pack();
btn2.config(state=DISABLED)"""
"os.system('nc -l -p 9999')"
'scan_ports("localhost",9999)'




