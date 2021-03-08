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
package net.simonix.dsl.jmeter.factory.listener

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class ListenerFactorySpec extends TempFileSpec {

    def "Check listeners generation"() {
        given: 'Test plan with listeners element'
        def config = configure {
            plan {
                aggregate(name: 'Aggregate', file: 'aggregate.jtl')
                summary(name: 'My Summary', file: 'example.jtl')

                backend(name: 'Backend Listener', enabled: false) {
                    arguments {
                        argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
                        argument(name: 'influxdbUrl', value: 'http://localhost:8086/write?db=jmeter')
                        argument(name: 'application', value: 'groovy')
                        argument(name: 'measurement', value: 'jmeter')
                        argument(name: 'summaryOnly', value: 'false')
                        argument(name: 'samplersRegex', value: '.*')
                        argument(name: 'percentiles', value: '90;95;99')
                        argument(name: 'testTitle', value: 'Groovy')
                        argument(name: 'eventTags', value: '')
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('listeners_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('listeners_0.jmx', resultFile)
    }
}
