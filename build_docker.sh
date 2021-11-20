#!/bin/sh

###
# Copyright 2019 Szymon Micyk
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###

# set -x

# create docker volume for grapes dependencies
docker volume create --name grapes-cache
# create proper ownership on grapes folder
docker run --rm --name grapes-container -v "grapes-cache":"/home/gradle/.groovy/grapes" alpine /bin/sh -c "chown 1000:1000 /home/gradle/.groovy/grapes"

docker run --rm -u gradle -w "/home/gradle/groovy-jmeter" -v "$(pwd)":"/home/gradle/groovy-jmeter" -v "grapes-cache":"/home/gradle/.groovy/grapes" gradle:7.0.2-jdk11 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build clean build publishIvyPublicationToIvyRepository
