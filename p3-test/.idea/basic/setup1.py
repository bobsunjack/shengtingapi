from cx_Freeze import setup,Executable
import os.path

PYTHON_INSTALL_DIR = r"E:\project\custom-projects\spark\venv\Scripts"#os.path.dirname(os.path.dirname(os.__file__))
os.environ['TCL_LIBRARY'] = r'E:\project\custom-projects\spark\venv\Lib\tcl8.6'
os.environ['TK_LIBRARY'] = r'E:\project\custom-projects\spark\venv\Lib\tk8.6'

options = {
      'build_exe': {
            'include_files':[
                  os.path.join(PYTHON_INSTALL_DIR, '', 'tk86t.dll'),
                  os.path.join(PYTHON_INSTALL_DIR, '', 'tcl86t.dll'),
            ],
      },
}

setup(name = "reandurllib" ,
      options = options,
      version = "0.1" ,
      description = "" ,
      executables = [Executable("basic-one.py")])
