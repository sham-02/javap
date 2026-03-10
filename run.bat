@echo off
cd /d "%~dp0"
set "DRIVER=mysql-connector-j-8.3.0.jar"

if not exist "%DRIVER%" (
    echo Downloading MySQL Connector/J...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar' -OutFile '%DRIVER%'"
)

if not exist "%DRIVER%" (
    echo Error: Could not download %DRIVER%. Please download it manually.
    pause
    exit /b 1
)

echo Compiling...
javac -cp ".;%DRIVER%" Register.java Login.java DatabaseSetup.java Dashboard.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running Application...
echo Classpath is: ".;%DRIVER%"
java -cp ".;%DRIVER%" DatabaseSetup
java -cp ".;%DRIVER%" Login
if %errorlevel% neq 0 (
    echo Application crashed.
)
pause
