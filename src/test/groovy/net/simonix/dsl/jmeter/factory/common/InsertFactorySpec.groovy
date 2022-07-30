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
package net.simonix.dsl.jmeter.factory.common

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class InsertFactorySpec extends TempFileSpec {

    def "Check insert from classpath"() {
        given: 'Test insert fragment from classpath file'
        def config = configure {
            plan {
                group {
                    insert file: 'net/simonix/dsl/jmeter/fragments/fragment.groovy'
                }
            }
        }

        File resultFile = tempFolder.newFile('insert_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('insert_0.jmx', resultFile)
    }

    def "Check insert generation with mixed level of nesting"() {
        given: 'Test plan with insert elements'

        def config = configure {
            plan {
                group {
                    insert file: 'net/simonix/dsl/jmeter/fragments/multiple.groovy'

                    http('GET /books') {
                        params {
                            param(name: 'param3', value: '3')

                            insert file: 'net/simonix/dsl/jmeter/fragments/params.groovy'
                        }
                    }

                    backend {
                        argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')

                        insert 'net/simonix/dsl/jmeter/fragments/arguments.groovy'
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('insert_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('insert_1.jmx', resultFile)
    }
}
