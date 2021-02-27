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

class AuthorizationFactorySpec extends TempFileSpec {

    def "Check authorizations generation"() {
        given: 'Test plan with authorizations'
        def config = configure {
            plan {
                authorizations()
                authorizations(name: 'Factory Authorization', comments: 'Factory Comments', enabled: false, clearEachIteration: true, useUserConfig: true) {
                    authorization(url: 'http://localhost/context', username: 'username', password: 'password', domain: 'localhost', realm: 'test', mechanism: 'BASIC')

                    authorization() // defaults values

                    authorization(mechanism: 'BASIC_DIGEST') // deprecated value
                    authorization(mechanism: 'BASIC')
                    authorization(mechanism: 'DIGEST')
                    authorization(mechanism: 'KERBEROS')
                }
            }
        }

        File resultFile = tempFolder.newFile('authorizations_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('authorizations_0.jmx', resultFile)
    }
}
