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
package net.simonix.dsl.jmeter.factory.common

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class ParamsFactorySpec extends TempFileSpec {

    def "Check params generation (short version)"() {
        expect: "configure test with shorts and save result to the file"
        def config = configure {
            plan(name: 'Factory Test Plan') {
                group {
                    defaults(protocol: 'http', domain: 'localhost', port: 80) {
                        params values: [
                                usr_var1: '1',
                                usr_var2: 'uservalue'
                        ]
                    }

                    http(name: 'GET /path', path: '/path') {
                        params values: [
                                http_var1: '2',
                                http_var2: 'uservalue2'
                        ]
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('shorts_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('shorts_0.jmx', resultFile)
    }
}
