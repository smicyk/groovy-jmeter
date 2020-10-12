# Groovy JMeter DSL

The *Groovy-JMeter* project is simple DSL to write JMeter test plans.

It has following features:
 - run scripts as standalone Groovy scripts, JUnit tests or as Gradle tasks
 - support of basic JMeter elements like controllers, groups, extractors and assertions
 - support of HTTP and JSR223 JMeter samplers
 - support of JMeter listeners (includes a listener with backed systems like influxdb)
 - add modularization of the script with *insert* keyword (can insert part of the other test script)
 
 Current version uses [Apache JMeter 5.1.1](https://archive.apache.org/dist/jmeter/binaries/) as runtime engine.
 
 > Note, that you don't have to download any components of JMeter to run the scripts, all necessary components are initialized at startup. 

## Prerequisites

Before start, you at least should have:

- [Java 8+](https://openjdk.java.net/)
- [Groovy 2.5.x](https://groovy.apache.org/download.html)
- [Gradle](https://gradle.org/releases/)

Or you can use the [Docker](https://www.docker.com/) approach (check the section below):

## How to start

Clone, build and publish jars to your local repository:

``` shell script
git clone https://github.com/smicyk/groovy-jmeter.git

gradle wrapper

gradlew clean build publish
```

You can also try alternative approach and build everything on *Docker* without installing anything on your machine:

```
git clone https://github.com/smicyk/groovy-jmeter.git

docker volume create --name grapes-cache

docker run --rm -u gradle -w "//home/gradle/groovy-jmeter" -v "/$(pwd)":"/home/gradle/groovy-jmeter" -v "grapes-cache":"/home/gradle/.groovy/grapes" gradle:5.6.4-jdk8 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build clean build publish
```

## First steps

To run your test script, it is enough to type in your favorite editor the following lines:

``` groovy

@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.scripts.jmeter.DefaultTestScript script

start {
    plan {
        group {
            http 'GET http://www.example.com'
        }
    }
}
```

This test plan will start one user and make call to *http://www.example.com*

To run it in command line, type:

``` shell script
groovy yourscriptname.groovy
``` 
If you want to start the same script inside a docker run the following command on command line:

``` shell script
docker run --rm -u groovy -v "$(pwd)":"/home/groovy" -v "grapes-cache":"/home/groovy/.groovy/grapes" groovy:2.5.9-jdk8 groovy yourscriptname.groovy
```

### DefaultTestScript vs. TestScript

There are two implementation for the test scripts, the *DefaultTestScript* and *TestScript*. The *DefaultTestScript* is very basic and doesn't have any additional features. The *TestScript* comes with additional command line support.

When you change the line with *@groovy.transform.BaseScript* annotation to *net.simonix.scripts.jmeter.TestScript*, so your file would look like this:

``` groovy

@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.scripts.jmeter.TestScript script

start {
    plan {
        group {
            http 'GET http://www.example.com'
        }
    }
}
```

You can execute your script with *help* parameter to see all available options:
``` shell script
groovy yourscriptname.groovy --help
Usage: groovy [-h] [--no-run] [--jmx-out=<file>] [-V=<variable=value>
              [=<variable=value>]...]...
  -h, --help             Show help message
      --jmx-out=<file>   Generate .jmx file based on the script
      --no-run           Execute the script but don't run the test
  -V, --vars=<variable=value>[=<variable=value>]...
                         Define values for placeholders in the script
```
Some interesting usage might be to generate .jmx file with additional variables which comes from environment:
``` groovy
@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.scripts.jmeter.TestScript script

start {
    plan {
        // HTTP defaults test element
        defaults protocol: 'http', domain: "${var_host}", port: var_port

        group (users: 10) {
            http 'GET /books'
        }
    }
}
```
To generate .jmx file from this script you can execute the following in the command line:
``` shell script
groovy yourscriptname.groovy --jmx-out yourscriptname.jmx --no-run -Vvar_host=localhost -Vvar_port=8080
```

For more examples you should check the *examples* folder and unit tests.

To get more information about all available options you should check groovy docs page or generate it from the source.
``` shell script
gradlew groovydoc

cd ./build/docs/groovydoc && index.html
```