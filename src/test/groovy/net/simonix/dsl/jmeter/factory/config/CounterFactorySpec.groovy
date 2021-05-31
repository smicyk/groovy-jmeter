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

class CounterFactorySpec extends TempFileSpec {

    def "Check counter generation"() {
        given: 'Test plan with counter config element'
        def config = configure {
            plan {
                counter(name: 'Factory counter', comments: 'Factory Comments', enabled: false, perUser: true, reset: true, start: 1, end: 10, increment: 2, variable: 'd', format: '0.0')
                counter() // default values
            }
        }

        File resultFile = tempFolder.newFile('counter_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('counter_0.jmx', resultFile)
    }
}
