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
package net.simonix.dsl.jmeter.factory.assertion

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class AssertionFactorySpec extends TempFileSpec {

    def "Check assertions generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    assert_response()
                    assert_response(name: 'Assert Response Factory', comments: 'Factory comments', applyTo: 'parent', field: 'response_headers', rule: 'matches', ignoreStatus: true, any: true, negate: true) {
                        pattern 'pattern \\d+'
                        pattern 'any string'
                    }
                    assert_duration(duration: 10)
                    assert_duration(name: 'Assert Duration Factory', comments: 'Factory Comments', applyTo: 'parent', duration: 10)
                    assert_duration 10

                    assert_json()
                    assert_json(name: 'Assert Json Factory', comments: 'Factory Comments', expression: '$.element', assertValue: true, assertAsRegex: false, value: 'test', expectNull: true, invert: true)

                    assert_jmes()
                    assert_jmes(name: 'Assert JMes Factory', comments: 'Factory Comments', expression: '$.element', assertValue: true, assertAsRegex: false, value: 'test', expectNull: true, invert: true)

                    assert_md5hex(value: '--some--value--')
                    assert_md5hex(name: 'Assert MD5 Factory', comments: 'Factory Comments', value: '--some-value--')
                    assert_md5hex '--some--value--'

                    assert_size(size: 50)
                    assert_size(name: 'Assert Size Factory', comments: 'Factory Comments', size: 50)
                    assert_size 50

                    assert_xpath()
                    assert_xpath(name: 'Assert XPath Factory', comments: 'Factory Comments', applyTo: 'parent', variable: 'var_variable', expression: '//span', ignoreWhitespace: true, validate: true, useNamespace: true, fetchDtd: true, failOnNoMatch: true, useTolerant: true, reportErrors: true, showWarnings: true, quiet: true)
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
