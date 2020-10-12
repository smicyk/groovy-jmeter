#!/bin/sh

docker network inspect network-books >/dev/null 2>&1 || \
    docker network create --driver bridge network-books

docker-compose up 
