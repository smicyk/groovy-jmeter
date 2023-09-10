@echo off

@rem
@rem Copyright 2020 Szymon Micyk
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem     http://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@rem create docker volume for grapes dependencies
docker volume create --name grapes-cache
@rem create proper ownership on grapes folder
docker run --rm --name grapes-container -v "grapes-cache":"/home/gradle/.groovy/grapes" alpine /bin/sh -c "chown 1000:1000 /home/gradle/.groovy/grapes"

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

docker run --rm -u gradle -w "/home/gradle/groovy-jmeter" -v %DIRNAME%:"/home/gradle/groovy-jmeter" -v "grapes-cache":"/home/gradle/.groovy/grapes" gradle:8.1.1-jdk11 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build clean build publishMavenPublicationToMavenLocal
