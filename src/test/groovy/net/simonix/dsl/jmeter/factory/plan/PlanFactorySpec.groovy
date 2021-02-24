package net.simonix.dsl.jmeter.factory.plan

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class PlanFactorySpec extends TempFileSpec {
    def "Check empty plan with default parameters generation"() {
        given: 'Test plan with empty body'
        def config = configure {
            plan {

            }
        }

        File resultFile = tempFolder.newFile('plan_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('plan_0.jmx', resultFile)
    }

    def "Check empty plan with all parameters set generation"() {
        given: 'Test plan with empty body'
        def config = configure {
            plan(name: 'Factory Test Plan', comments: "Factory Comment", enabled: false, serialized: true, functionalMode: true, tearDownOnShutdown: true) {

            }
        }

        File resultFile = tempFolder.newFile('plan_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('plan_1.jmx', resultFile)
    }

    def "Check plan with user define variables generation"() {
        given: 'Test plan with user define variables in the body'
        def config = configure {
            plan(name: 'Factory Test Plan', comments: "Factory Comment", enabled: false, serialized: true, functionalMode: true, tearDownOnShutdown: true) {
                arguments {
                    argument(name: 'usr_var1', value: '1')
                    argument(name: 'usr_var2', value: 'uservalue')
                }
            }
        }

        File resultFile = tempFolder.newFile('plan_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('plan_2.jmx', resultFile)
    }

    def "Check plan with user define variables (short version) generation"() {
        given: 'Test plan with user define variables in the body'
        def config = configure {
            plan {
                arguments values: [
                        usr_var1: '1',
                        usr_var2: 'uservalue'
                ]
            }
        }

        File resultFile = tempFolder.newFile('plan_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('plan_3.jmx', resultFile)
    }
}
