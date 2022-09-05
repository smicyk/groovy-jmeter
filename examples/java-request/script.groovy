@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

import JavaRequestTest

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        group(users: 10, rampUp: 1) {
            java_request classname: JavaRequestTest.name, {
                arguments {
                    argument name: 'code', value: '200'
                    argument name: 'message', value: 'Hello'
                    argument name: 'label', value: 'User No. ${__threadNum}'
                }
            }
        }

        summary(file: 'script.jtl', enabled: true)
    }
}
