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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class DefaultsFactorySpec extends TempFileSpec {

    def "Check defaults generation"() {
        given: 'Test plan with defaults config element'
        def config = configure {
            plan {
                defaults(name: 'Factory Defaults', comments: "Factory Comment", enabled: false, protocol: 'https', domain: 'localhost', port: 8080, method: 'POST', path: '/context', encoding: 'UTF-8', saveAsMD5: true)
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

    def "Check defaults proxy generation"() {
        given: 'Test plan with defaults element and proxy'
        def config = configure {
            plan {
                defaults {
                    proxy scheme: 'http', host: 'my.proxy.com', port: '80', username: 'test', password: 'test'
                }
            }
        }

        File resultFile = tempFolder.newFile('defaults_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('defaults_1.jmx', resultFile)
    }

    def "Check defaults embedded resources generation"() {
        given: 'Test plan with defaults element and embedded resources'
        def config = configure {
            plan {
                defaults {
                    resources parallel: 7, urlInclude: 'http://example\\.invalid/.*', urlExclude: '.*\\.(?i:svg|png)'
                }
            }
        }

        File resultFile = tempFolder.newFile('defaults_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('defaults_2.jmx', resultFile)
    }

    def "Check defaults source generation"() {
        given: 'Test plan with defaults element and source element'

        def config = configure {
            plan {
                defaults {
                    source type: 'hostname', address: 'example.com'
                }
            }
        }

        File resultFile = tempFolder.newFile('defaults_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('defaults_3.jmx', resultFile)
    }

    def "Check defaults timeout generation"() {
        given: 'Test plan with defaults element and timeout element'
        def config = configure {
            plan {
                defaults {
                    timeout connect: 5000, response: 10000
                }
            }
        }

        File resultFile = tempFolder.newFile('defaults_4.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('defaults_4.jmx', resultFile)
    }
}
