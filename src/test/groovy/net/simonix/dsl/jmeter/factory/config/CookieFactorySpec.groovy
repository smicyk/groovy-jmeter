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

class CookieFactorySpec extends TempFileSpec {

    def "Check cookies generation"() {
        given: 'Test plan with cookies config element'
        def config = configure {
            plan {
                cookies()
                cookies(name: 'Factory cookies', comments: 'Factory Comments', enabled: false, clearEachIteration: true, useUserConfig: true, policy: 'standard') {
                    cookie()
                    cookie(secure: true, path: '/context', domain: 'localhost', name: 'JSESSIONID', value: '223EFE34432DFDF', expires: 1000)
                }
                cookies(policy: 'standard')
                cookies(policy: 'compatibility')
                cookies(policy: 'netscape')
                cookies(policy: 'standard-strict')
                cookies(policy: 'best-match')
                cookies(policy: 'rfc2109')
                cookies(policy: 'rfc2965')
                cookies(policy: 'default')
                cookies(policy: 'ignoreCookies')
            }
        }

        File resultFile = tempFolder.newFile('cookies_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('cookies_0.jmx', resultFile)
    }
}
