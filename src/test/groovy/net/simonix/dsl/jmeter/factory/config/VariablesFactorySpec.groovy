/*
 * Copyright 2021 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class VariablesFactorySpec extends TempFileSpec {

    def "Check user variables generation"() {
        given: 'Test plan with user variables config element'
        def config = configure {
            plan {
                variables(name: 'Factory User Variables', comments: "Factory Comment", enabled: false) {
                    variable(name: 'variable', value: 'value', description: 'description')
                }
            }
        }

        File resultFile = tempFolder.newFile('variables_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('variables_0.jmx', resultFile)
    }

    def "Check user variables generation (short version)"() {
        given: 'Test plan with user variables config element (short version)'
        def config = configure {
            plan {
                variables values: [
                        var1: 1,
                        var2: 'value'
                ]
            }
        }

        File resultFile = tempFolder.newFile('variables_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('variables_1.jmx', resultFile)
    }
}
