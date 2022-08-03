package net.simonix.dsl.jmeter.plugins.dummy.sampler

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save
import static net.simonix.dsl.jmeter.TestScriptRunner.plugins

class DummyFactorySpec extends TempFileSpec {

    def "Dummy sampler plugin generation"() {
        given: 'Test plan with http element'

        plugins {
            id 'net.simonix.dsl.jmeter.plugins.dummy'
        }

        def config = configure {
            plan {
                group {
                    ext_dummy()
                    ext_dummy(name: 'Factory dummy', comments: "Factory Comment", enabled: false)
                    ext_dummy(name: 'Factory dummy', comments: "Factory Comment", enabled: false, waiting: true, successful: true, responseCode: '200', responseMessage: 'OK', requestData: 'data', responseData: 'data', responseTime: '1000000', latency: '1000', connect: '1000', url: 'http://www.example.com', resultClass: 'org.apache.jmeter.samplers.SamplerResult')
                }
            }
        }

        File resultFile = tempFolder.newFile('dummy_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('dummy_0.jmx', resultFile)
    }
}