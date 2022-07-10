@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        group(users: 10, rampUp: 1) {
            include 'dummy_plugin.jmx'
        }

        summary(file: 'script.jtl', enabled: true)
    }
}

