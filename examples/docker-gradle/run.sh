#!/bin/sh

docker volume create --name gradle-cache

docker network inspect network-books >/dev/null 2>&1 || \
    docker network create --driver bridge network-books

docker run --rm -u gradle -w "/home/gradle" -v "$(pwd)":"/home/gradle" -v "gradle-cache":"/home/gradle/.gradle" -v "grapes-cache":"/home/gradle/.groovy/grapes" --network network-books gradle:7.0.2-jdk8 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build
