# Groovy JMeter DSL

The *Groovy-JMeter* project is simple DSL to write JMeter test plans.

![Build & Test](https://github.com/smicyk/groovy-jmeter/actions/workflows/build.yml/badge.svg)

It has following features:
 - keep JMeter test files (*.jmx) as a code (Groovy scripts)
 - run scripts as standalone scripts, JUnit tests or Gradle tasks
 - support of basic JMeter elements like controllers, groups, extractors and assertions
 - support of HTTP, JSR223 and JDBC JMeter samplers
 - support of JMeter listeners (includes a listener with backed systems like influxdb)
 - add modularization of the script with *insert* keyword (can insert part of the other test script)

 Current version uses [Apache JMeter 5.6](https://archive.apache.org/dist/jmeter/binaries/) as runtime engine.

 > Note, that you don't have to download any components of JMeter to run the scripts, all necessary components are initialized at startup.
 
 > Checkout the project [wiki](https://github.com/smicyk/groovy-jmeter/wiki) for quick reference of all keywords and properties

 > Check [Component Status](https://github.com/smicyk/groovy-jmeter/wiki/Components-Status) page for supported JMeter features

## Prerequisites

Before start, you should have:

- [Java 11+](https://openjdk.java.net/)
- [Groovy 3.0.17](https://groovy.apache.org/download.html)
- [Gradle 8.1.1](https://gradle.org/releases/) (needed only for building from source)

Or you can use the [Docker](https://www.docker.com/) approach (check the section below):

## How to start

> Starting from version 0.11.0, all artifacts are available through [maven repository](https://mvnrepository.com/artifact/net.simonix.scripts/groovy-jmeter). If you want the latest changes, go to [Building from source](#building-from-source).

## First steps

To run your test script, it is enough to type in your favorite editor the following lines:

```groovy
@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.DefaultTestScript script

start {
    plan {
        group {
            http 'GET http://www.example.com'
        }

        // optional element, shows execution progress
        summary(file: 'log.jtl')
    }
}

```

This test plan will start one user and make call to *http://www.example.com*

> Please remember that first execution of the script can take some time as Ivy must download all dependency to local cache

To run the script, execute the following command:

```shell script
groovy yourscriptname.groovy
``` 
If you want to start the same script inside a docker, execute commands below:

```shell script
# one time setup for script dependencies, it will speed up future executions
docker volume create --name grapes-cache

# in Linux
docker run --rm -u groovy -v "$(pwd)":"/home/groovy" -v "grapes-cache":"/home/groovy/.groovy/grapes" groovy:3.0.17-jdk11 groovy yourscriptname.groovy

# in Windows
docker run --rm -u groovy -v %CD%:"/home/groovy" -v "grapes-cache":"/home/groovy/.groovy/grapes" groovy:3.0.17-jdk11 groovy yourscriptname.groovy
```

Typical output on console should look like this:
```shell script
+      1 in 00:00:01 =    1,7/s Avg:   419 Min:   419 Max:   419 Err:     0 (0,00%) Active: 1 Started: 1 Finished: 0
=      1 in 00:00:01 =    1,7/s Avg:   419 Min:   419 Max:   419 Err:     0 (0,00%)
```

## Test plan generator

There is [*.har converter](https://github.com/smicyk/groovy-harventer) to generate scripts from *.har files. It can greatly speed up scripts generation for your tests. 

## DefaultTestScript vs. TestScript

There are two implementation for the test scripts, the *DefaultTestScript* and *TestScript*. The *DefaultTestScript* is very basic and doesn't have any additional features. The *TestScript* comes with additional command line support.

When you change the line with *@groovy.transform.BaseScript* annotation to *net.simonix.dsl.jmeter.TestScript*, so your file would look like this:

```groovy

@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        group {
            http 'GET http://www.example.com'
        }

        // optional element, shows execution progress
        summary(file: 'log.jtl')
    }
}

```

You can execute your script with *help* parameter to see all available options:
```shell script
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
```groovy
@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        // HTTP defaults test element
        defaults protocol: 'http', domain: "${var_host}", port: var_port

        group (users: 10) {
            http 'GET /books'
        }

        // optional element, shows execution progress
        summary(file: 'log.jtl')
    }
}

```
To generate .jmx file from this script you can execute the following in the command line:
```shell script
groovy yourscriptname.groovy --jmx-out yourscriptname.jmx --no-run -Vvar_host=localhost -Vvar_port=8080
```

For more examples you should check the [examples](examples) folder and [unit tests](src/test).

To get more information about all available options you should check groovy docs page or generate it from the source.
```shell script
gradlew groovydoc

cd ./build/docs/groovydoc && index.html
```

## More advanced example

The example below shows more constructs related to testing simple REST API.

```groovy
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
            csv(file: 'users.csv', variables: ['var_username', 'var_password'], delimiter: ';')

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
        summary(file: 'script.jtl', enabled: true)
    }
}

```
The next example shows testing database with JMeter.
```groovy
@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')
@Grab('org.postgresql:postgresql:42.2.20') // download JDBC driver for your database

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        before {
            jdbc datasource: 'postgres', {
                pool connections: 10, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
                connection url: 'jdbc:postgresql://database-db:5432/', driver: 'org.postgresql.Driver', username: 'postgres', password: 'postgres'
                init(['SET search_path TO public'])
                validation idle: true, timeout: 5000, query: '''SELECT 1'''
            }

            jdbc use: 'postgres', {
                jdbc_preprocessor use: 'postgres', {
                    execute('''
                        DROP TABLE employee
                    ''')
                }

                execute('''
                    CREATE TABLE employee (id INTEGER PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(50), salary INTEGER)
                ''')
            }
        }

        group users: 100, rampUp: 60, loops: 1, {
            csv file: 'employees.csv', delimiter: ',', ignoreFirstLine: true, variables: ['var_id', 'var_first_name', 'var_last_name', 'var_email', 'var_salary']

            jdbc use: 'postgres', {
                execute('''
                    INSERT INTO employee (id, first_name, last_name, email, salary) VALUES(?, ?, ?, ?, ?)
                ''') {
                    params {
                        param value: '${var_id}', type: 'INTEGER'
                        param value: '${var_first_name}', type: 'VARCHAR'
                        param value: '${var_last_name}', type: 'VARCHAR'
                        param value: '${var_email}', type: 'VARCHAR'
                        param value: '${var_salary}', type: 'INTEGER'
                    }
                }
            }

            jdbc use: 'postgres', {
                query(limit: 1, result: 'var_employee_count', inline: '''
                    SELECT count(*) FROM employee
                ''')

                jsrpostprocessor(inline: '''
                    log.info('Number of employees: ' + vars.get('var_employee_count'))
                ''')
            }

            // output to .jtl file
            summary(file: 'script.jtl', enabled: true)
        }
    }
}

```

## Building from source

Clone, build and publish jars to your local repository:

```shell script
git clone https://github.com/smicyk/groovy-jmeter.git

# in Linux
./gradlew clean build publishIvyPublicationToIvyRepository

# in Windows
gradlew clean build publishIvyPublicationToIvyRepository
```

You can also try alternative approach and build everything on *Docker* without installing anything on your machine:

```shell script
git clone https://github.com/smicyk/groovy-jmeter.git

docker volume create --name grapes-cache

# in Linux
docker run --rm -u gradle -w "/home/gradle/groovy-jmeter" -v "$(pwd)":"/home/gradle/groovy-jmeter" -v "grapes-cache":"/home/gradle/.groovy/grapes" gradle:8.1.1-jdk11 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build clean build publishIvyPublicationToIvyRepository

# in Windows
docker run --rm -u gradle -w "/home/gradle/groovy-jmeter" -v "%CD%":"/home/gradle/groovy-jmeter" -v "grapes-cache":"/home/gradle/.groovy/grapes" gradle:8.1.1-jdk11 gradle -Dorg.gradle.project.buildDir=/tmp/gradle-build clean build publishIvyPublicationToIvyRepository
```

## Conventions

There are several conventions used in the design of the DSL.

### Naming

All names in the DSL should make the script easy to read and concise. Most of the keywords and properties names are single words. The examples of such keywords might be: __plan__, __group__, __loops__. A similar rule applies to properties, e.g. __name__, __comments__, __forever__.
However, some keywords must have two words like __execute_if__, __extract_regx__, __assert_json__. The longer names for properties are in camel case, e.g. rampUp, perUser.

### Users vs. Threads

In JMeter world, the users and threads are used interchangeably (both means virtual concurrent users executing test plan). In the script, we use the users as a convention. Check the example below:

```groovy
start {
    plan {
        group users: 10, {
            // define random variable 'var_random' for each user (in other words each user has its own random generator)
            random(minimum: 0, maximum: 100, variable: 'var_random', perUser: true)
        }
    }
}

```

### Default values

All keywords in the DSL has predefined default values for its properties. For example, each keyword has **name** property with a default value defined. If there is no name property given for the keyword, the script will use the default value. You can check default values in Groovy docs. Below are more examples:

```groovy
start {
    // would be same as plan (name: 'Test Plan')
    plan { 

    }

    plan {
        // would be same as group (users: 1, rampUp: 1) 
        group {

        }
    }
}

```

### Required properties

Even though each property has a default value, sometimes there is no sense to have a test element without specific property value. Such properties are required and raise an exception if missing. Check the example below:

> Please note that in JMeter documentation there are many properties which are required. Still, in the DSL we make them only required if they vital to execution, otherwise they have some reasonable default value.

```groovy
start {
    plan {
        group {
            // condition property is required, otherwise using execute_if controller has no sense
            execute_if (condition: '''__jexl3(vars.get('var_random') mod 2 == 0)''') {

            }
        }
    }
}

```

### Groovy as DSL

There are several things to keep in mind while writing the scripts; most of the stuff relates to Groovy language:

* using different quotes around string, please refer to [Groovy docs](https://groovy-lang.org/syntax.html#all-strings}) about string and quotes and check example below:

```groovy

start {
    // using single quotes (for Java plain String)
    plan (name: 'Test name')
    // using single quotes is recommended in most situation (should be used when you want use JMeter variable substitution in the script)
    plan (name: '${var_variable}')
    // using double quotes (for GString, interpolation available during test build but not execution by JMeter engine)
    plan (name: "${var_param}")
}

```
* there are several ways to use keywords, check the example below:

```groovy
start {
    // keyword without properties, after keyword you can open closure without any properties or parenthesis
    plan {

    }
    // keyword with properties, the properties of the keyword can have properties defined as key/value pair 
    plan (name: 'test', comments: 'new test plan') {

    }
    // keyword with properties but without parenthesis, please note that after all properties you must put comma
    plan name: 'test', comments: 'new test plan', {

    }
    // keyword without properties and child test elements, note that the parenthesis must be used
    plan()
}

```
### Shortcuts

There are many places where we can use shortcuts to define the same thing:

* the first argument for the keyword can be simple value. In most cases it is treated as a name for test element:
```groovy
start {
    // long version
    plan name: 'Test plan111'
    // short version
    plan 'Test plan222'
    // short version with properties
    plan 'Test plan', enabled: true
}

```
* some keywords treat first value as shortcut
```groovy
start {
    plan {
        group {
            // long version
            loop count: 10
            // short version
            loop 10

            // long version
            execute_if condition: '''__jexl3(vars.get('var_random') mod 2 == 0)'''
            // short version
            execute_if '''__jexl3(vars.get('var_random') mod 2 == 0)'''
        }
    }
}

```
* there is special syntax for HTTP sampler regarding first argument
```groovy
start {
    plan {
        group {
            // long version for HTTP request
            http(protocol: 'http', domain: 'localhost', port: 8080, path: '/app/login', method: 'GET')
            
            // short version for HTTP request
            http 'GET http://localhost:8080/app/login'
        }

        // if used with defaults keyword, some elements can be ommitted
        group {
            defaults(protocol: 'http', domain: 'localhost', port: 8080)

            http 'GET /app/login'
        }
    }
}

```
* simplified array like structures for samplers and config elements
```groovy
start {
    // long version to define user variables inside test plan
    plan {
        arguments {
            argument (name: 'var_variable', value: 'value')
            argument (name: 'var_other_variable', value: 'other_value')
        }
    }
    // short version to define user variables inside test plan
    plan {
        arguments values: [
                var_variable: 'value',
                var_other_veriable: 'other_value'
        ]
    }
    // there are others test elements which has simplified behaviour e.g. params, variables, headers
    plan {
        group {
            // long version for params
            http 'GET http://www.example.com', {
                params {
                    param(name: 'param', value: 'value')
                    param(name: 'other', value: 'other')
                }
            }
            // short version for params
            http 'GET http://www.example.com', {
                params values: [
                        param: 'value',
                        other: 'other'
                ]
            }
        }
    }
}

```
* by default samples have names after its name property, but in case of HTTP request there are some differences: 
```groovy
start {
    plan {
        arguments values: [ var_bookId: 'value' ]

        group {
            // the name of the sample will generated as 'GET /app/books/:var_bookId'
            // currently this is default behaviour only if short version is used
            http 'GET http://localhost/app/books/${var_bookId}'
            // to define own sample name you must use long version
            http name: 'Custom Name', protocol: 'http', domain: 'localhost', path: '/app/books/${var_bookId}', method: 'GET'
        }
    }
}

```
