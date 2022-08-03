@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        variables {
            variable name: 'var_apikey', value: '${__P(var_apikey)}'
        }

        group(users: 10, rampUp: 1) {
            http 'GET http://www.example.com'
        }

        backend classname: 'org.datadog.jmeter.plugins.DatadogBackendClient', {
            argument(name: 'apiKey', value: '${var_apikey}')
            argument(name: 'datadogUrl', value: 'https://api.datadoghq.com/api/')
            argument(name: 'logIntakeUrl', value: 'https://http-intake.logs.datadoghq.com/v1/input/')
            argument(name: 'metricsMaxBatchSize', value: '200')
            argument(name: 'logsBatchSize', value: '500')
            argument(name: 'sendResultsAsLogs', value: 'true')
            argument(name: 'includeSubresults', value: 'false')
            argument(name: 'samplersRegex', value: '')
            argument(name: "customTags", value: '')
        }

        summary(file: 'script.jtl', enabled: true)
    }
}

