@echo off

docker volume create --name grapes-cache

docker network inspect network-books > NUL || docker network create --driver bridge network-books

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

docker-compose up 
