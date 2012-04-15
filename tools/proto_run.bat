@echo off

cd ..
xcopy src\resources\maps bin\resources\maps /e /i /h /q /y

cd bin
java application.Application
pause