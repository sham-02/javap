@echo off
echo Compiling Java files...
javac *.java

echo Starting Login Application...
java -cp ".;mysql-connector-j-8.3.0.jar" Login

pause
