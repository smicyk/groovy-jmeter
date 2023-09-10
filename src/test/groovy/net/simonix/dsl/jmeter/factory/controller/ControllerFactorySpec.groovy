/*
 * Copyright 2023 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.controller

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class ControllerFactorySpec extends TempFileSpec {

    def "Check execute controllers generation"() {
        given: 'Test plan with controllers element'
        def config = configure {
            plan {
                group {
                    execute(type: 'if', condition: '${var_condition} == 5', useExpression: false, evaluateAll: true) {
                    }
                    execute(type: 'while', condition: '${var_condition} == 5') {
                    }
                    execute(type: 'once') {
                    }
                    execute(type: 'interleave', ignore: true, accrossUsers: true) {
                    }
                    execute(type: 'random', ignore: true) {
                    }
                    execute(type: 'order') {
                    }
                    execute(type: 'percent', percent: 50, perUser: true) {
                    }
                    execute(type: 'total', total: 50, perUser: true) {
                    }
                    execute(type: 'runtime', runtime: 5) {
                    }
                    execute(type: 'switch', value: 1) {
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('controllers_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('controllers_0.jmx', resultFile)
    }

    def "Check logic controllers generation"() {
        given: 'Test plan with controllers element'
        def config = configure {
            plan {
                group {
                    simple {
                    }

                    loop(count: 10, forever: true) {
                    }

                    section(lock: 'test_lock') {
                    }

                    for_each(in: 'var_in', out: 'var_out', start: 1, end: 10, separator: false) {
                    }

                    transaction(timers: true, generate: true) {
                    }

                    include('/path/testplan.jmx')
                }
            }
        }

        File resultFile = tempFolder.newFile('controllers_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('controllers_1.jmx', resultFile)
    }

    def "Check include raw generation"() {
        given: 'Two tests plan one created standard way and other with include_raw element'
        def config = configure {
            plan {
                backend {
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

        def configRaw = configure {
            plan {
                include_raw '''
                <BackendListener guiclass="BackendListenerGui" testclass="BackendListener" testname="Backend Listener" enabled="true">
                    <elementProp name="arguments" elementType="Arguments">
                        <collectionProp name="Arguments.arguments">
                            <elementProp name="influxdbMetricsSender" elementType="Argument">
                                <stringProp name="Argument.name">influxdbMetricsSender</stringProp>
                                <stringProp name="Argument.value">org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender</stringProp>
                            </elementProp>
                            <elementProp name="influxdbUrl" elementType="Argument">
                                <stringProp name="Argument.name">influxdbUrl</stringProp>
                                <stringProp name="Argument.value">http://localhost:8086/write?db=jmeter</stringProp>
                            </elementProp>
                            <elementProp name="application" elementType="Argument">
                                <stringProp name="Argument.name">application</stringProp>
                                <stringProp name="Argument.value">groovy</stringProp>
                            </elementProp>
                            <elementProp name="measurement" elementType="Argument">
                                <stringProp name="Argument.name">measurement</stringProp>
                                <stringProp name="Argument.value">jmeter</stringProp>
                            </elementProp>
                            <elementProp name="summaryOnly" elementType="Argument">
                                <stringProp name="Argument.name">summaryOnly</stringProp>
                                <stringProp name="Argument.value">false</stringProp>
                            </elementProp>
                            <elementProp name="samplersRegex" elementType="Argument">
                                <stringProp name="Argument.name">samplersRegex</stringProp>
                                <stringProp name="Argument.value">.*</stringProp>
                            </elementProp>
                            <elementProp name="percentiles" elementType="Argument">
                                <stringProp name="Argument.name">percentiles</stringProp>
                                <stringProp name="Argument.value">90;95;99</stringProp>
                            </elementProp>
                            <elementProp name="testTitle" elementType="Argument">
                                <stringProp name="Argument.name">testTitle</stringProp>
                                <stringProp name="Argument.value">Groovy</stringProp>
                            </elementProp>
                            <elementProp name="eventTags" elementType="Argument">
                                <stringProp name="Argument.name">eventTags</stringProp>
                            </elementProp>
                        </collectionProp>
                    </elementProp>
                    <stringProp name="classname">org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient</stringProp>
                </BackendListener>
                '''
            }
        }

        File resultFile = tempFolder.newFile('controllers_2.1.jmx')
        File resultFileRaw = tempFolder.newFile('controllers_2.2.jmx')

        when: 'save test to file'
        save(config, resultFile)
        save(configRaw, resultFileRaw)

        then: 'check if both files are the same'
        filesAreTheSame(resultFile, resultFileRaw)
    }
}
