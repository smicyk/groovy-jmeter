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
import org.apache.http.conn.DnsResolver

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class DnsFactorySpec extends TempFileSpec {

    def "Check dns manager generation"() {
        given: 'Test plan with dns manager config element'
        def config = configure {
            plan {
                dns()
                dns(name: 'Factory DNS', comments: 'Factory Comments', enabled: false, clearEachIteration: true, useSystem: false, servers: [ '217.173.198.3', '193.111.144.161']) {
                    host 'www.example.com', address: '123.123.123.123'
                    host name: 'www.example.com', address: '123.123.123.123'
                }
                dns(name: 'Factory DNS', comments: 'Factory Comments', enabled: false, clearEachIteration: true, useSystem: false, servers: [ '217.173.198.3', '193.111.144.161'], values: [
                        'www.example.com': '123.123.123.132',
                        'www.example1.com': '123.123.123.123'
                ])

                dns(useSystem: false)
                dns(useSystem: true, servers: [ '217.173.198.3', '193.111.144.161'])
                dns(servers: [ '217.173.198.3', '193.111.144.161'])
            }
        }

        File resultFile = tempFolder.newFile('dns_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('dns_0.jmx', resultFile)
    }
}
