/*
 * Copyright 2020 Szymon Micyk
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

import net.simonix.dsl.jmeter.model.ValidationException
import spock.lang.Specification

import static net.simonix.dsl.jmeter.TestScriptRunner.configure

class PropertyValidationSpec extends Specification {

    def "Plan with invalid properties"() {
        when: "configure test plan with invalid properties"

        configure {
            plan (invalidProperty: 'test') {

            }
        }

        then: "test fails to build"
        thrown ValidationException
    }

    def "Plan without required properties"() {
        when: "configure test plan without required properties"

        configure {
            plan {
                group {
                    execute_if {

                    }
                }
            }
        }

        then: "test fails to build"
        thrown ValidationException
    }
}
