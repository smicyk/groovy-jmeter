/*
 * Copyright 2022 Szymon Micyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            plan(name: 'Factory Test Plan', comments: "Factory Comment", enabled: false, serialized: true, functionalMode: true, tearDownOnShutdown: true, classpath: ['listeners.jar', 'functions.jar']) {

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
            plan(name: 'Factory Test Plan', comments: "Factory Comment", enabled: false, serialized: true, functionalMode: true, tearDownOnShutdown: true, classpath: ['listeners.jar', 'functions.jar']) {
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
