@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        arguments {
            argument(name: 'var_host', value: "${var_host}")
            argument(name: 'var_user', value: "${var_user}")
            argument(name: 'var_pass', value: "${var_pass}")
        }

        defaults(protocol: 'http', domain: '${var_host}', port: 1080)

        group(users: var_users, rampUp: var_ramp) {
            cookies(name: 'cookies manager')

            http('POST /login') {
                params {
                    param(name: 'username', value: '${var_user}')
                    param(name: 'password', value: '${var_pass}')
                }

                extract_regex expression: '"trackId", content="([0-9]+)"', variable: 'var_trackId'
            }

            http('GET /api/books') {
                params values: [ limit: '10' ]

                extract_json expressions: '$..id', variables: 'var_bookId'
            }

            http('GET /api/books/${var_bookId}') {
                extract_json expressions: '$..author.id', variables: 'var_authorId'
            }

            // simple form with parenthesis
            http 'GET /api/authors/${var_authorId}'

            // if controller with expression and posting json data
            random(minimum: 0, maximum: 10000, variable: 'var_random')
            execute_if('''__jexl3(vars.get('var_random') mod 2 == 0)''') {
                headers values: [ 'Content-Type': 'application/json' ]

                http('POST /api/books/${var_bookId}/comments') {
                    body """\
                        {
                            "title": "Short comment",
                            "content": "Short content"
                        }
                    """

                    check_response {
                        status() eq 200
                    }
                }
            }

            loop(count: 10) {
                execute_random {
                    http('GET /api/books') {
                        params values: [ search: 'New title', limit: '10', trackId: '${trackId}' ]
                    }
                }
            }
        }

        summary(file: 'script.jtl', enabled: true)

        backend(name: 'InfluxDb Backend', enabled: true) {
            arguments {
                argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
                argument(name: 'influxdbUrl', value: 'http://localhost:8086/write?db=jmeter')
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

