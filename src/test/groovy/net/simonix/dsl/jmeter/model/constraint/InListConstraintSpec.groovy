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
package net.simonix.dsl.jmeter.model.constraint

import spock.lang.Specification

class InListConstraintSpec extends Specification  {

    def "matches range with numbers"() {
        given:
        InListPropertyConstraint constraint = Constraints.inList(['value1', 'value2'])

        expect:
        constraint.matches(value) == result

        where:
        value        || result
        'value1'     || true
        'value2'     || true
        'value3'     || false
    }

    def "matches range with expression"() {
        given:
        InListPropertyConstraint constraint = Constraints.inList(['value1', 'value2'])

        expect:
        constraint.matches(value) == result

        where:
        value              || result
        'value1'           || true
        'value2'           || true
        '${var_value}'     || true
    }
}
