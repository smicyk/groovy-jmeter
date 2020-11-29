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
package net.simonix.dsl.jmeter.model.constraint

import spock.lang.Specification


class RangePropertyConstraintSpec extends Specification  {

    def "matches range with numbers"() {
        given:
        RangePropertyConstraint constraint = Constraints.range(-10, 10)

        expect:
        constraint.matches(value) == result

        where:
        value || result
        0     || true
        4     || true
        -4    || true
        10    || true
        -10   || true
        20    || false
        -20   || false
    }

    def "matches range with BigInteger"() {
        given:
        RangePropertyConstraint constraint = Constraints.range(-10, 10)

        expect:
        constraint.matches(value) == result

        where:
        value || result
        BigInteger.valueOf(0)     || true
        BigInteger.valueOf(4)     || true
        BigInteger.valueOf(-4)    || true
        BigInteger.valueOf(10)    || true
        BigInteger.valueOf(-10)   || true
        BigInteger.valueOf(20)    || false
        BigInteger.valueOf(-20)   || false
    }

    def "matches range with String"() {
        given:
        RangePropertyConstraint constraint = Constraints.range(-10, 10)

        expect:
        constraint.matches(value) == result

        where:
        value || result
        '0'     || true
        '4'     || true
        '-4'    || true
        '10'    || true
        '-10'   || true
        '20'    || false
        '-20'   || false
    }
}
