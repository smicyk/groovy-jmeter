@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')
@Grab('org.postgresql:postgresql:42.2.20')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    // define basic test plan
    plan {
        group users: 100, rampUp: 60, loops: 10, {
            csv file: 'employees.csv', delimiter: ',', ignoreFirstLine: true, variables: ['var_id', 'var_first_name', 'var_last_name', 'var_email', 'var_salary']

            defaults domain: 'graphql-api', port: 5000

            headers {
                header name: 'Content-Type', value: 'application/json'
            }

            graphql 'POST /graphql', {
                execute '''
                    mutation createEmployee($input: CreateEmployeeInput!) {
                        createEmployee(input: $input) {
                            employee {
                                id
                            }
                        }
                    }
                '''
                // currently variables has a bug https://github.com/apache/jmeter/pull/660 which prevents numerical/boolean variables in json
                variables '''
                    {
                        "input": {
                            "employee": {
                                "id": "${var_id}",
                                "firstName": "${var_first_name}",
                                "lastName": "${var_last_name}",
                                "email": "${var_email}",
                                "salary": "${var_salary}"
                            }
                        }
                    }
                '''
            }

            graphql 'POST /graphql', {
                execute '''
                    query {
                        allEmployees {
                            nodes {
                                salary
                            }
                        }
                    }
                '''
            }

            // output to .jtl file
            summary(file: 'script.jtl', enabled: true, requestHeaders: true, responseData: true, responseHeaders: true, xml: true, samplerData: true)

            // gather execution information influxdb backend
            backend(name: 'InfluxDb Backend', enabled: true) {
                arguments {
                    argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
                    argument(name: 'influxdbUrl', value: 'http://graphql-influx:8086/write?db=jmeter')
                    argument(name: 'application', value: 'employees')
                    argument(name: 'measurement', value: 'performance')
                    argument(name: 'summaryOnly', value: 'false')
                    argument(name: 'samplersRegex', value: '.*')
                    argument(name: 'percentiles', value: '90;95;99')
                    argument(name: 'testTitle', value: 'employees - users: 100, rampup: 60')
                    argument(name: "eventTags", value: '')
                }
            }
        }
    }
}
