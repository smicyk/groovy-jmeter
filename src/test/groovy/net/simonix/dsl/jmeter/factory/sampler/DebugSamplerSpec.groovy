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
package net.simonix.dsl.jmeter.factory.sampler

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save;

class DebugSamplerSpec extends TempFileSpec {
    def "Check debug generation"() {
        given: 'Test plan with ajp element'
        def config = configure {
            plan {
                group {
                    debug()
                    debug(name: 'Factory Debug', comments: "Factory Comment", enabled: false)
                    debug(displayJMeterProperties: true, displayJMeterVariables: false, displaySystemProperties: true)
                }
            }
        }

        File resultFile = tempFolder.newFile('debug_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('debug_0.jmx', resultFile)
    }
}
