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
package net.simonix.dsl.jmeter.factory.group

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class GroupFactorySpec extends TempFileSpec {

    def "Check group generation"() {
        given: 'Test plan with group element'
        def config = configure {
            plan {
                before(name: 'Factory Setup', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, scheduler: true, delay: 10, duration: 10, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
                group(name: 'Factory Group', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, delayedStart: 10, scheduler: true, delay: 10, duration: 10, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
                after(name: 'Factory TearDown', comments: "Factory Comment", enabled: false, users: 10, rampUp: 60, scheduler: true, delay: 10, duration: 10, loops: 2, forever: true, onError: 'stop_test', keepUser: false)
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
}
