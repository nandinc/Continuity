@echo off
echo ----------------------------------
echo Building nand Continuity
echo ----------------------------------
rmdir bin /s /q
md bin
javac -classpath src\ -d bin src\model\*.java
javac -classpath src\ -d bin src\model\exception\*.java
javac -classpath src\ -d bin src\debug\*.java
javac -classpath src\ -d bin src\application\*.java

xcopy src\resources\maps bin\resources\maps /e /i /h /q /y
xcopy src\resources\graphics bin\resources\graphics /e /i /h /q /y

echo Done.
pause