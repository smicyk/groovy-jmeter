#!/bin/bash

docker volume create --name grapes-cache

docker network inspect network-books >/dev/null 2>&1 || \
    docker network create --driver bridge network-books

docker run --rm -u groovy -v "$(pwd)":"/home/groovy" -v "grapes-cache":"/home/groovy/.groovy/grapes" --network network-books groovy:3.0.7-jdk11 groovy script.groovy -Vhost_ip=mockserver-books -Vinflux_ip=influxdb-books
