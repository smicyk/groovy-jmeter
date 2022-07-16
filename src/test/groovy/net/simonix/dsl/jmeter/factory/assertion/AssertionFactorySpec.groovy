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

                    assert_size(size: 4096)
                    assert_size(name: 'Assert Size Factory', comments: 'Factory Comments', size: 4096)
                    assert_size 4096

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

    def "Check assertion DSL generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    check_response {
                        status() ne 200 and 201 and 202, 'is not 2xx'
                        status() eq 210 and 420 and 530
                        status() ne 201 or 402 or 503
                        status() eq 210 or 420 or 530

                        status() substring '500' and '400'
                        status() substring '501' or '401'

                        status() includes 'pattern \\d+' and 'test 123', 'doesn\'t have email'
                        status() includes 'pattern \\d+' or 'test 123'
                        status() matches 'pattern \\d+' and 'test 123'
                        status() matches 'pattern \\d+' or 'test 123'

                        headers() excludes 'COOKIES', 'no cookies'
                        headers() includes 'COOKIES', 'has cookies'

                        text() excludes 'test text'
                        text() includes 'test text'

                        document() excludes 'some text'
                        document() includes 'some text'

                        message() ne 'OK', 'Not OK'
                        message() eq 'OK', 'OK'

                        url() excludes 'localhost', 'external'
                        url() includes 'localhost', 'local'

                        duration() eq 2000
                        md5hex() eq '2bf7b8126bb7c645638e444f6e2c58a5'
                    }
                    check_request {
                        headers() excludes 'COOKIES', 'no cookies'
                        headers() includes 'COOKIES', 'has cookies'

                        text() excludes 'test text'
                        text() includes 'test text'
                    }
                    check_size {
                        status() eq 4096
                        status() ne 4096
                        status() gt 4096
                        status() lt 4096
                        status() ge 4096
                        status() le 4096

                        headers() eq 4096
                        text() eq 4096
                        body() eq 4096
                        message() eq 4096
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

    def "Check assertion DSL with properties generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    check_response {
                        status(name: 'Check Name', comments: 'Check Comments', enabled: false) ne 200 and 201 and 202, 'is not 2xx'
                        status(name: 'Check Name', comments: 'Check Comments', enabled: false) substring '500' and '400'
                        status(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'pattern \\d+' or 'test 123'
                        status(name: 'Check Name', comments: 'Check Comments', enabled: false) matches 'pattern \\d+' and 'test 123'
                        status('Check Name', comments: 'Check Comments', enabled: false) matches 'pattern \\d+' and 'test 123'
                        status('Check Name') matches 'pattern \\d+' and 'test 123'

                        headers(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'COOKIES', 'has cookies'
                        headers('Check Name', comments: 'Check Comments', enabled: false) includes 'COOKIES', 'has cookies'
                        headers('Check Name') includes 'COOKIES', 'has cookies'

                        text(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'test text'
                        text('Check Name', comments: 'Check Comments', enabled: false) includes 'test text'
                        text('Check Name') includes 'test text'

                        document(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'some text'
                        document('Check Name', comments: 'Check Comments', enabled: false) includes 'some text'
                        document('Check Name') includes 'some text'

                        message(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 'OK', 'OK'
                        message('Check Name', comments: 'Check Comments', enabled: false) eq 'OK', 'OK'
                        message('Check Name') eq 'OK', 'OK'

                        url(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'localhost', 'local'
                        url('Check Name', comments: 'Check Comments', enabled: false) includes 'localhost', 'local'
                        url('Check Name') includes 'localhost', 'local'

                        duration(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 2000
                        duration('Check Name', comments: 'Check Comments', enabled: false) eq 2000
                        duration('Check Name') eq 2000

                        md5hex(name: 'Check Name', comments: 'Check Comments', enabled: false) eq '2bf7b8126bb7c645638e444f6e2c58a5'
                        md5hex('Check Name', comments: 'Check Comments', enabled: false) eq '2bf7b8126bb7c645638e444f6e2c58a5'
                        md5hex('Check Name') eq '2bf7b8126bb7c645638e444f6e2c58a5'
                    }
                    check_request {
                        headers(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'COOKIES', 'has cookies'
                        headers('Check Name', comments: 'Check Comments', enabled: false) includes 'COOKIES', 'has cookies'
                        headers('Check Name') includes 'COOKIES', 'has cookies'

                        text(name: 'Check Name', comments: 'Check Comments', enabled: false) includes 'test text'
                        text('Check Name', comments: 'Check Comments', enabled: false) includes 'test text'
                        text('Check Name') includes 'test text'
                    }
                    check_size {
                        status(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        status('Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        status('Check Name') eq 4096

                        headers(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        headers('Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        headers('Check Name') eq 4096

                        text(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        text('Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        text('Check Name') eq 4096

                        body(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        body('Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        body('Check Name') eq 4096

                        message(name: 'Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        message('Check Name', comments: 'Check Comments', enabled: false) eq 4096
                        message('Check Name') eq 4096
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('assertions_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('assertions_2.jmx', resultFile)
    }

    def "Check assertion DSL with properties override generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    check_response enabled: false, {
                        status(enabled: true) ne 200, 'is not 2xx'
                        status(enabled: false) ne 200, 'is not 2xx'
                        status() ne 200, 'is not 2xx'

                        headers(enabled: true) includes 'COOKIES', 'has cookies'
                        headers(enabled: false) includes 'COOKIES', 'has cookies'
                        headers() includes 'COOKIES', 'has cookies'

                        text(enabled: true) includes 'test text'
                        text(enabled: false) includes 'test text'
                        text() includes 'test text'

                        document(enabled: true) includes 'some text'
                        document(enabled: false) includes 'some text'
                        document() includes 'some text'


                        message(enabled: true) eq 'OK', 'OK'
                        message(enabled: false) eq 'OK', 'OK'
                        message() eq 'OK', 'OK'


                        url(enabled: true) includes 'localhost', 'local'
                        url(enabled: false) includes 'localhost', 'local'
                        url() includes 'localhost', 'local'

                        duration(enabled: true) eq 2000
                        duration(enabled: false) eq 2000
                        duration() eq 2000

                        md5hex(enabled: true) eq '2bf7b8126bb7c645638e444f6e2c58a5'
                        md5hex(enabled: false) eq '2bf7b8126bb7c645638e444f6e2c58a5'
                        md5hex() eq '2bf7b8126bb7c645638e444f6e2c58a5'
                    }
                    check_request enabled: false, {
                        headers(enabled: true) includes 'COOKIES', 'has cookies'
                        headers(enabled: false) includes 'COOKIES', 'has cookies'
                        headers() includes 'COOKIES', 'has cookies'

                        text(enabled: true) includes 'test text'
                        text(enabled: false) includes 'test text'
                        text() includes 'test text'
                    }
                    check_size enabled: false, {
                        status(enabled: true) eq 4096
                        status(enabled: false) eq 4096
                        status() eq 4096

                        headers(enabled: true) eq 4096
                        headers(enabled: false) eq 4096
                        headers() eq 4096

                        text(enabled: true) eq 4096
                        text(enabled: false) eq 4096
                        text() eq 4096

                        body(enabled: true) eq 4096
                        body(enabled: false) eq 4096
                        body() eq 4096

                        message(enabled: true) eq 4096
                        message(enabled: false) eq 4096
                        message() eq 4096
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('assertions_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('assertions_3.jmx', resultFile)
    }
}
