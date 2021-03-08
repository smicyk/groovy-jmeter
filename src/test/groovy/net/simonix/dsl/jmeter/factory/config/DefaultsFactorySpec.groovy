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

class DefaultsFactorySpec extends TempFileSpec {

    def "Check defaults generation"() {
        given: 'Test plan with defaults config element'
        def config = configure {
            plan {
                defaults(name: 'Factory Defaults', comments: "Factory Comment", enabled: false, protocol: 'https', domain: 'localhost', port: 8080, method: 'POST', path: '/context', encoding: 'UTF-8')
                defaults()
                defaults {
                    params {
                        param(name: 'param1', value: 'value1')
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('defaults_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('defaults_0.jmx', resultFile)
    }
}
