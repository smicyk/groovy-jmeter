@echo off

docker volume create --name grapes-cache

docker network inspect network-books > NUL || docker network create --driver bridge network-books

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

docker run --rm -u groovy -v %DIRNAME%:"/home/groovy" -v "grapes-cache":"/home/groovy/.groovy/grapes" --network network-books groovy:3.0.7-jdk11 groovy script.groovy -Vhost_ip=mockserver-books -Vinflux_ip=influxdb-books
