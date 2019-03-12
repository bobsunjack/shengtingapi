import one
import sys
import tkinter
from test import  jsonone


list=["one","two"]
list.append("全国")

print(len(list))
print(list[2])

del list[1]

tup=()

dict1={}

set1={1,2}
set1.add(3)
print(len(set1))

var1=2

if var1==3:
    print("a")
elif var1==4:
    print("b")
else:
    print("c")



top = tkinter.Tk()
# 进入消息循环
top.mainloop()
