/*
 * Copyright 2019 Szymon Micyk
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
package net.simonix.dsl.jmeter

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class FactoryBuilderSpec extends TempFileSpec {

    def "Check counter generation"() {
        given: 'Test plan with counter config element'
        def config = configure {
            plan {
                counter(name: 'Factory counter', comments: 'Factory Comments', enabled: false, perUser: true, reset: true, start: 1, end: 10, increment: 2, variable: 'd', format: '0.0')
                counter() // default values
            }
        }

        File resultFile = tempFolder.newFile('counter_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('counter_0.jmx', resultFile)
    }

    def "Check csv generation"() {
        given: 'Test plan with csv config element'
        def config = configure {
            plan {
                csv(name: 'Factory csv', comments: 'Factory Comments', enabled: false, ignoreFirstLine: true, allowQuotedData: true, recycle: false, stopUser: true, file: 'test.csv', encoding: 'UTF-16', variables: ['header1', 'header2'], delimiter: ';', shareMode: 'all')
                csv(file: 'test.csv', variables: ['header'])
                csv(shareMode: 'all', file: 'test.csv', variables: ['header'])
                csv(shareMode: 'group', file: 'test.csv', variables: ['header'])
                csv(shareMode: 'thread', file: 'test.csv', variables: ['header'])
            }
        }

        File resultFile = tempFolder.newFile('csv_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('csv_0.jmx', resultFile)
    }

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

    def "Check headers generation"() {
        given: 'Test plan with header manager config element'
        def config = configure {
            plan {
                headers(name: 'Factory Headers', comments: "Factory Comment", enabled: false)
                headers {
                    header(name: 'header1', value: 'value1')
                    header(name: 'header2', value: 'value2')
                }
                headers values: [
                        header1: 'value1',
                        header2: 'value2'
                ]
            }
        }

        File resultFile = tempFolder.newFile('headers_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('headers_0.jmx', resultFile)
    }

    def "Check login generation"() {
        given: 'Test plan with login config element'
        def config = configure {
            plan {
                login(name: 'Factory Login', comments: "Factory Comment", enabled: false, username: 'username', password: 'password')
                login()
            }
        }

        File resultFile = tempFolder.newFile('login_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('login_0.jmx', resultFile)
    }

    def "Check random variable generation"() {
        given: 'Test plan with random config element'
        def config = configure {
            plan {
                random(name: 'Factory Random', comments: "Factory Comment", enabled: false, perUser: false, minimum: 1, maximum: 10, format: '0.0', variable: 'variable', seed: '5')
                random()
            }
        }

        File resultFile = tempFolder.newFile('random_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('random_0.jmx', resultFile)
    }

    def "Check user variables generation"() {
        given: 'Test plan with user variables config element'
        def config = configure {
            plan {
                variables(name: 'Factory User Variables', comments: "Factory Comment", enabled: false) {
                    variable(name: 'variable', value: 'value', description: 'description')
                }
            }
        }

        File resultFile = tempFolder.newFile('variables_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('variables_0.jmx', resultFile)
    }

    def "Check user variables generation (short version)"() {
        given: 'Test plan with user variables config element (short version)'
        def config = configure {
            plan {
                variables values: [
                        var1: 1,
                        var2: 'value'
                ]
            }
        }

        File resultFile = tempFolder.newFile('variables_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('variables_1.jmx', resultFile)
    }

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

    def "Check jsr generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    jsrsampler('inline': 'vars.put("var_groovy", "123")', cacheKey: false, file: 'test.groovy', language: 'groovy') {
                        jsrassertion('inline': 'vars.get("var_groovy") == "123"', cacheKey: false, file: 'test.groovy', language: 'groovy')
                    }
                    jsrtimer('inline': '10', cacheKey: false, file: 'test.groovy', language: 'groovy')

                    jsrsampler('inline': 'vars.put("var_groovy", "123")', cacheKey: false, file: 'test.groovy', language: 'groovy') {
                        jsrpostprocessor('inline': 'true', cacheKey: false, file: 'test.groovy', language: 'groovy')
                        jsrpreprocessor('inline': 'false', cacheKey: false, file: 'test.groovy', language: 'groovy')
                    }
                    jsrlistener('inline': 'true', cacheKey: false, file: 'test.groovy', language: 'groovy')
                }
            }
        }

        File resultFile = tempFolder.newFile('jsr_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('jsr_0.jmx', resultFile)
    }

    def "Check assertions generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    assert_response(applyTo: 'parent', field: 'response_headers', rule: 'matches', ignoreStatus: true, any: true, negate: true) {
                        pattern 'pattern \\d+'
                        pattern 'any string'
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('assertions_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('assertions_0.jmx', resultFile)
    }

    def "Check check generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    check_response {
                        status(not) eq 200 and 201 and 202, 'is not 2xx'
                        status() eq 210 and 420 and 530
                        status(not) eq 201 or 402 or 503
                        status() eq 210 or 420 or 530

                        status() substring '500' and '400'
                        status() substring '501' or '401'

                        status() contains 'pattern \\d+' and 'test 123', 'doesn\'t have email'
                        status() contains 'pattern \\d+' or 'test 123'
                        status() matches 'pattern \\d+' and 'test 123'
                        status() matches 'pattern \\d+' or 'test 123'

                        headers(not) contains 'COOKIES', 'no cookies'
                        headers() contains 'COOKIES', 'has cookies'

                        text(not) contains 'test text'
                        text() contains 'test text'

                        document(not) contains 'some text'
                        document() contains 'some text'

                        message(not) eq 'OK', 'Not OK'
                        message() eq 'OK', 'OK'

                        url(not) contains 'localhost', 'external'
                        url() contains 'localhost', 'local'

                        duration() eq 2000
                        md5hex() eq '2bf7b8126bb7c645638e444f6e2c58a5'
                    }
                    check_request {
                        headers(not) contains 'COOKIES', 'no cookies'
                        headers() contains 'COOKIES', 'has cookies'

                        text(not) contains 'test text'
                        text() contains 'test text'
                    }
                    check_size {
                        status() eq 200
                        status() ne 200
                        status() gt 200
                        status() lt 200
                        status() ge 200
                        status() le 200

                        headers() eq 200
                        text() eq 200
                        body() eq 200
                        message() eq 200
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('assertions_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('assertions_1.jmx', resultFile)
    }
}
