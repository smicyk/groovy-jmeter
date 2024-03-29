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
package net.simonix.dsl.jmeter.factory.sampler

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class AjpFactorySpec extends TempFileSpec {

    def "Check default ajp generation"() {
        given: 'Test plan with ajp element'
        def config = configure {
            plan {
                ajp()
                ajp(name: 'Factory Ajp', comments: "Factory Comment", enabled: false)
                ajp(protocol: 'http', domain: 'example.com', port: 80, path: '/', method: 'GET', encoding: 'UTF-8', autoRedirects: true, followRedirects: false, keepAlive: false, multipart: true, browserCompatibleMultipart: true, saveAsMD5: true) {
                    params {
                        param(name: 'param1', value: 'value1')
                        param(name: 'param2', value: 'value2')
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('ajp_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('ajp_0.jmx', resultFile)
    }

    def "Check ajp embedded resources generation"() {
        given: 'Test plan with ajp element'
        def config = configure {
            plan {
                ajp 'GET http://www.example.com', {
                    resources parallel: 7, urlInclude: 'http://example\\.invalid/.*', urlExclude: '.*\\.(?i:svg|png)'
                }

                ajp 'GET http://www.example.com', {
                    resources urlInclude: 'http://example\\.invalid/.*', urlExclude: '.*\\.(?i:svg|png)'
                }

                ajp 'GET http://www.example.com', {
                    resources()
                }

                ajp 'GET http://www.example.com'
            }
        }

        File resultFile = tempFolder.newFile('ajp_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('ajp_1.jmx', resultFile)
    }
}
