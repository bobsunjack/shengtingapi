import time

fo = open("foo.txt", "a+")
while True:
    time_str="Start : %s" % time.ctime()
    fo.write(time_str+"\n")
    fo.flush()
    time.sleep( 5 )


