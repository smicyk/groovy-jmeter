@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    // define basic test plan
    plan {
        // add user define variables
        arguments {
            argument(name: 'var_host', value: 'mockserver-app')
        }

        // define HTTP default values
        defaults(protocol: 'http', domain: '${var_host}', port: 1080)

        // define group of 1000 users with ramp up period 300 seconds
        group(users: 1000, rampUp: 300) {
            // add cookie manager for HTTP requests
            cookies(name: 'cookies manager')

            // define POST request with parameters and extract tracking id for later use
            http('POST /login') {
                params {
                    param(name: 'username', value: 'john')
                    param(name: 'password', value: 'john')
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

            // 'if' controller with expression and posting json data
            random(minimum: 0, maximum: 10000, variable: 'var_random')
            execute_if('''__jexl3(vars.get('var_random') mod 2 == 0)''') {
                headers values: [ 'Content-Type': 'application/json' ]

                http('POST /api/books/${var_bookId}/comments') {
                    body '''\
                        {
                            "title": "Short comment",
                            "content": "Short content"
                        }
                    '''

                    // check assertion about HTTP status code in the response
                    check_response {
                        status() eq 200
                    }
                }
            }

            // loop controller
            loop(count: 10) {
                execute_random {
                    http('GET /api/books') {
                        params values: [ search: 'New title', limit: '10', trackId: '${trackId}' ]
                    }
                }
            }
        }

        // output to .jtl file
        summary(file: 'script.jtl', enabled: true)

        // gather execution information influxdb backend
        backend(name: 'InfluxDb Backend', enabled: true) {
            arguments {
                argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
                argument(name: 'influxdbUrl', value: 'http://influxdb:8086/write?db=jmeter')
                argument(name: 'application', value: 'books')
                argument(name: 'measurement', value: 'performance')
                argument(name: 'summaryOnly', value: 'false')
                argument(name: 'samplersRegex', value: '.*')
                argument(name: 'percentiles', value: '90;95;99')
                argument(name: 'testTitle', value: 'books - users: 1000, rampup: 300')
                argument(name: "eventTags", value: '')
            }
        }
    }
}
