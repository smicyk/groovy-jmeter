@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')
@Grab('net.simonix.scripts:plugins-dummy')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

plugins {
    id 'net.simonix.dsl.jmeter.plugins.dummy'
}

start {
    plan {
        group(users: 10, rampUp: 1) {
            ext_dummy(successful: true)
        }

        summary(file: 'script.jtl', enabled: true)
    }
}