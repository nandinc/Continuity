@echo off
echo ----------------------------------
echo Building nand Continuity Prototype
echo ----------------------------------
:: cd ..
rmdir bin /s /q
md bin
javac -classpath src\ -d bin src\model\*.java
javac -classpath src\ -d bin src\model\exception\*.java
javac -classpath src\ -d bin src\debug\*.java
javac -classpath src\ -d bin src\controller\console\*.java
javac -classpath src\ -d bin src\controller\console\exception\*.java
javac -classpath src\ -d bin src\ui\console\*.java
javac -classpath src\ -d bin src\application\*.java

xcopy src\resources\maps bin\resources\maps /e /i /h /q /y

echo Done.
pause