import datetime
import os
import re

now_time = datetime.datetime.now()
print(now_time.ctime())
print(now_time.time())

path = '/root/Download/dir/'

pathb = '{}{}'.format(path,'bb')

str_list="unix  2      [ ACC ]     STREAM     LISTENING     38550    private/proxywrite"
regex = re.compile('\s+')
ret_list = regex.split(str_list)
print(ret_list[7])
print(pathb)