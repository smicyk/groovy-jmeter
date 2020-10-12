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

import net.simonix.dsl.jmeter.test.spec.LogSamplerSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.run

class SimpleControllersSpec extends LogSamplerSpec {
    
    def "loops test element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group {
                    // default values
                    loop {
                        log('loop 1')
                    }

                    loop(count: 3) {
                        log('loop 2')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "All loops should be visible"

        1 * listener.listen('loop 1')
        3 * listener.listen('loop 2')
    }
    
    def "groups test element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group {
                    log('group 1')
                }
                group(users: 3) {
                    log('group 2')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "All loops should be visible"

        1 * listener.listen('group 1')
        3 * listener.listen('group 2')
    }
}
