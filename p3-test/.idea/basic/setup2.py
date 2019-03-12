from distutils.core import setup
import py2exe
'''
setup(
    console=[{"script": "basic-one.py", "icon_resources": [(1, u"a.ico")] }]
)
'''


setup(console = ["basic-one.py"])