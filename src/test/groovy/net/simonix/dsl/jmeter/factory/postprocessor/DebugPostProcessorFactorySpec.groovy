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