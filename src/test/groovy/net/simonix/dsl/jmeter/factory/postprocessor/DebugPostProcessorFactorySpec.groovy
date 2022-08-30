package net.simonix.dsl.jmeter.factory.postprocessor

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class DebugPostProcessorFactorySpec extends TempFileSpec {
    def "Check debug generation"() {
        given: 'Test plan with debug postprocess element'
        def config = configure {
            plan {
                group {
                    debug() {
                        debug_postprocessor()
                        debug_postprocessor(name: 'Factory Debug', comments: "Factory Comment", enabled: false)
                        debug_postprocessor(displayJMeterProperties: true, displayJMeterVariables: false, displaySystemProperties: true, displaySamplerProperties: false)
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('debug_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('debug_1.jmx', resultFile)
    }
}