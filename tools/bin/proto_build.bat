@echo off
echo ----------------------------------
echo Building nand Continuity Prototype
echo ----------------------------------
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

javac -classpath tools\;tools\lib\diffutils-1.2.1.jar -d bin tools\TestRunner.java
javac -classpath tools\;tools\lib\diffutils-1.2.1.jar -d bin tools\TestGUI.java

xcopy tools\resources\tests\input bin\tools\resources\tests\input /e /i /h /q /y
xcopy tools\resources\tests\output bin\tools\resources\tests\output /e /i /h /q /y
xcopy tools\lib bin\lib /e /i /h /q /y

echo Done.
pause