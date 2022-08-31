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

class JavaRequestFactorySpec extends TempFileSpec {
    def "Check java_request generation"() {
        given: 'Test plan with java_request element'
        def config = configure {
            plan {
                group {
                    java_request(classname: 'org.apache.jmeter.protocol.java.test.JavaTest')
                    java_request(name: 'Factory Java', comments: "Factory Comment", enabled: false, classname: 'org.apache.jmeter.protocol.java.test.JavaTest')
                    java_request(classname: 'org.apache.jmeter.protocol.java.test.JavaTest') {
                        arguments {
                            argument(name: 'Sleep_Time', value: '100')
                            argument(name: 'Sleep_Mask', value: '0xFF')
                            argument(name: 'Label', value: 'Java Sample')
                            argument(name: 'ResponseCode', value: '400')
                            argument(name: 'ResponseMessage', value: 'Not OK')
                            argument(name: 'Status', value: 'NO_OK')
                            argument(name: 'SamplerData', value: '')
                            argument(name: 'ResultData', value: '')
                        }
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('java_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('java_0.jmx', resultFile)
    }
}
