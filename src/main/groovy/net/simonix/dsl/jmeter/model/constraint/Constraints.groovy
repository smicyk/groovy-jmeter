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

final class Constraints {

    static InListPropertyConstraint inList(List<String> values) {
        return new InListPropertyConstraint(values)
    }

    static RangePropertyConstraint range(long from, long to) {
        return new RangePropertyConstraint(from, to)
    }

    static RangePropertyConstraint range(long from) {
        return new RangePropertyConstraint(from, Long.MAX_VALUE)
    }

    static NotEmptyContraint notEmpty() {
        return new NotEmptyContraint()
    }

    static NotNullConstraint notNull() {
        return new NotNullConstraint()
    }
}
