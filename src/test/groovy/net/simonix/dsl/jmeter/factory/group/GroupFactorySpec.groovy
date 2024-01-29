/*
 * Copyright 2024 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.group

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class GroupFactorySpec extends TempFileSpec {

    def "Check group generation"() {
        given: 'Test plan with group element'
        def config = configure {
            plan {
                before(name: 'Factory Setup', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, scheduler: true, delay: 10, duration: 101, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
                group(name: 'Factory Group', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, delayedStart: 10, scheduler: true, delay: 10, duration: 101, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
                after(name: 'Factory TearDown', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, scheduler: true, delay: 10, duration: 101, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
            }
        }

        File resultFile = tempFolder.newFile('group_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('group_0.jmx', resultFile)
    }

    def "Check default group generation"() {
        given: 'Test plan with group element without any configuration'
        def config = configure {
            plan {
                before()
                group()
                after()
            }
        }

        File resultFile = tempFolder.newFile('group_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('group_1.jmx', resultFile)
    }

    def "Check group generation with expression"() {
        given: 'Test plan with group element'
        def config = configure {
            plan {
                variables values: [
                        'var_users': 10,
                        'var_rampUp': 60,
                        'var_delay': 10,
                        'var_delayedStart': 10,
                        'var_duration': 101,
                        'var_loops': 2,
                        'var_onError': 'stop_test'
                ]

                before(users: '${var_users}', rampUp: '${var_rampUp}', scheduler: true, delay: '${var_delay}', duration: '${var_duration}', loops: '${var_loops}', forever: false, onError: '${var_onError}', keepUser: false)
                group(users: '${var_users}', rampUp: '${var_delay}', delayedStart: '${var_delayedStart}', scheduler: true, delay: '${var_delay}', duration: '${var_duration}', loops: '${var_loops}', forever: false, onError: '${var_onError}', keepUser: false)
                after(users: '${var_users}', rampUp: '${var_duration}', scheduler: true, delay: '${var_delay}', duration: '${var_duration}', loops: '${var_loops}', forever: false, onError: 'stop_test', keepUser: false)
            }
        }

        File resultFile = tempFolder.newFile('group_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('group_2.jmx', resultFile)
    }

    def "Check open model group generation with expression"() {
        given: 'Test plan with schedule element'
        def config = configure {
            plan {
                schedule inline: '''
                    rate(1/min) random_arrivals(10 min) pause(1 min)
                ''', {

                }

            }
        }

        File resultFile = tempFolder.newFile('group_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('group_3.jmx', resultFile)
    }

    def "Check group behaviour for infinite configuration"() {
        given: 'Test plan with schedule element'
        def config = configure {
            plan {
                group loops: 1, {
                    // default behaviour
                }

                group forever: true, {
                    // loops set to -1
                }

                group loops: 2, forever: true, {
                    // loops set to -1, forever overrides
                }

                group loops: 2, forever: false, {
                    // loops set to 2, forever not used
                }

                group loops: 0, {
                    // loops 0, no execution
                }
            }
        }

        File resultFile = tempFolder.newFile('group_4.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('group_4.jmx', resultFile)
    }
}
