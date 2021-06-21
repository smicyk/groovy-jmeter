@echo off

docker volume create --name gradle-cache

docker network inspect network-books > NUL || docker network create --driver bridge network-books

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

docker run --rm -u gradle -w "/home/gradle" -v %DIRNAME%:"/home/gradle" -v "gradle-cache":"/home/gradle/.gradle" -v "grapes-cache":"/home/gradle/.groovy/grapes" --network network-books gradle:7.0.2-jdk8 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build
