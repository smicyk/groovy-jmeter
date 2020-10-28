# Groovy JMeter DSL

The *Groovy-JMeter* project is simple DSL to write JMeter test plans.

It has following features:
 - keep JMeter test files (*.jmx) as a code (Groovy scripts)
 - run scripts as standalone scripts, JUnit tests or Gradle tasks
 - support of basic JMeter elements like controllers, groups, extractors and assertions
 - support of HTTP and JSR223 JMeter samplers
 - support of JMeter listeners (includes a listener with backed systems like influxdb)
 - add modularization of the script with *insert* keyword (can insert part of the other test script)
 
 Current version uses [Apache JMeter 5.1.1](https://archive.apache.org/dist/jmeter/binaries/) as runtime engine.
 
 > Note, that you don't have to download any components of JMeter to run the scripts, all necessary components are initialized at startup.
 
 > Note, if you are Windows user, you can copy command from *.sh files and run them on command line (or install some console emulator like [Cmder](https://cmder.net/)).

## Prerequisites

Before start, you should have:

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

For more examples you should check the [examples](examples) folder and [unit tests](src/test).

To get more information about all available options you should check groovy docs page or generate it from the source.
``` shell script
gradlew groovydoc

cd ./build/docs/groovydoc && index.html
```

### More advanced example

The example below shows more constructs related to testing simple REST API.

``` groovy
@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    // define basic test plan
    plan {
        // add user define variables (this will be defined on Test Plan not as separate User Defined Variables test element)
        arguments {
            argument(name: 'var_host', value: 'prod.mycompany.com')
        }

        // define HTTP default values
        defaults(protocol: 'http', domain: '${var_host}', port: 1080)

        // define group of 1000 users with ramp up period 300 seconds
        group(users: 1000, rampUp: 300) {
            // add cookie manager for HTTP requests
            cookies(name: 'cookies manager')

            // load users data from file into variables
            csv(filename: 'users.csv', variables: ['var_username', 'var_password'], delimiter: ';')

            // define POST request with parameters and extract tracking id for later use
            http('POST /login') {
                params {
                    param(name: 'username', value: '${var_username}')
                    param(name: 'password', value: '${var_password}')
                }

                extract_regex expression: '"trackId", content="([0-9]+)"', variable: 'var_trackId'
            }

            // define GET request and extract data from json response
            http('GET /api/books') {
                params values: [ limit: '10' ]

                extract_json expressions: '$..id', variables: 'var_bookId'
            }

            http('GET /api/books/${var_bookId}') {
                extract_json expressions: '$..author.id', variables: 'var_authorId'
            }

            // simplified form of HTTP request, no parenthesis
            http 'GET /api/authors/${var_authorId}'

            // define simple controller to make POST request
            simple {
                headers values: [ 'Content-Type': 'application/json' ]

                http('POST /api/books/${var_bookId}/comments') {
                    body '''\
                        {
                            "title": "Title",
                            "content": "Comment content"
                        }
                    '''

                    // check assertion about HTTP status code in the response
                    check_response {
                        status() eq 200
                    }
                }
            }
        }

        // output to .jtl file
        summary(path: 'script.jtl', enabled: true)
    }
}
```