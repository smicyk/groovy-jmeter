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

class FileFactorySpec extends TempFileSpec {
    def "Check files generation"() {
        given: 'Test plan with http element'
        def config = configure {
            plan {
                http {
                    files {
                        file 'test.txt', name: 'param1', type: 'text/plain'
                        file file: 'test.pdf', name: 'param2', type: 'application/pdf'
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('files_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('files_0.jmx', resultFile)
    }
}
