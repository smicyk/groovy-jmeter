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
package net.simonix.dsl.jmeter.factory.controller

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class ControllerFactorySpec extends TempFileSpec {

    def "Check execute controllers generation"() {
        given: 'Test plan with controllers element'
        def config = configure {
            plan {
                group {
                    execute(type: 'if', condition: '${var_condition} == 5', useExpression: false, evaluateAll: true) {
                    }
                    execute(type: 'while', condition: '${var_condition} == 5') {
                    }
                    execute(type: 'once') {
                    }
                    execute(type: 'interleave', ignore: true, accrossUsers: true) {
                    }
                    execute(type: 'random', ignore: true) {
                    }
                    execute(type: 'order') {
                    }
                    execute(type: 'percent', percent: 50, perUser: true) {
                    }
                    execute(type: 'total', total: 50, perUser: true) {
                    }
                    execute(type: 'runtime', runtime: 5) {
                    }
                    execute(type: 'switch', value: 1) {
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('controllers_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('controllers_0.jmx', resultFile)
    }

    def "Check logic controllers generation"() {
        given: 'Test plan with controllers element'
        def config = configure {
            plan {
                group {
                    simple {
                    }

                    loop(count: 10, forever: true) {
                    }

                    section(lock: 'test_lock') {
                    }

                    for_each(in: 'var_in', out: 'var_out', start: 1, end: 10, separator: false) {
                    }

                    transaction(timers: true, generate: true) {
                    }

                    include('/path/testplan.jmx')
                }
            }
        }

        File resultFile = tempFolder.newFile('controllers_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('controllers_1.jmx', resultFile)
    }
}
