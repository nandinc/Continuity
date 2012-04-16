#!/bin/bash

# build proto
javac `find ../src -name "*.java"` -d ./bin

# move map files
cp -R ../src/resources/maps ./bin/resources/

# build testrunner
javac -classpath ./:./lib/diffutils-1.2.1.jar -d ./bin ./TestRunner.java
javac -classpath ./:./lib/diffutils-1.2.1.jar -d ./bin ./TestGUI.java

mkdir ./bin/tools/
mkdir ./bin/tools/resources
cp -R ./resources/tests/ ./bin/tools/resources/
cp -R ./lib ./bin/
