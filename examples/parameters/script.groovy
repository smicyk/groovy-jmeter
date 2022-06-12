@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        arguments {
            argument(name: 'var_host', value: "${var_host}")
            argument(name: 'var_port', value: "${var_port}")
            argument(name: 'var_user', value: "${var_user}")
            argument(name: 'var_pass', value: "${var_pass}")

            argument(name: 'var_users', value: '${__P(var_users, 2)}')
            argument(name: 'var_ramp', value: '${__P(var_ramp, 5)}')
            argument(name: 'var_range', value: '${__P(var_range, 6000)}')
            argument(name: 'var_delay', value: '${__P(var_delay, 2000)}')
        }

        defaults(protocol: 'http', domain: '${var_host}', port: '${var_port}')

        group(users: '${var_users}', rampUp: '${var_ramp}') {
            uniform_timer(range: '${var_range}', delay: '${var_delay}')

            http('POST /login') {
                params {
                    param(name: 'username', value: '${var_user}')
                    param(name: 'password', value: '${var_pass}')
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