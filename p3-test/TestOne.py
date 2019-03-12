class MyClass:
    """this is test"""
    i=122
    def f(self):
        return "hello world"+self.r

    def __init__(self,name):
        self.r=name




x=MyClass("钱")
print(x.f())
