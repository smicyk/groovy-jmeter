@echo off

docker network inspect network-books > NUL || docker network create --driver bridge network-books

docker-compose up 
