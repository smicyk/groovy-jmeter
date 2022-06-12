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

import groovy.transform.CompileDynamic
import groovy.transform.Immutable

@Immutable
@CompileDynamic
class RangePropertyConstraint implements PropertyConstraint {

    Long from
    Long to

    @Override
    boolean matches(Object value) {
        if (value instanceof Collection) {
            for (Object v: (Collection) value) {
                if (!contains(v)) {
                    return false
                }
            }

            return true
        }

        return contains(value)
    }

    @Override
    String description() {
        return "range ${from}..${to}"
    }

    private boolean contains(Object value) {
        if (value instanceof Integer) {
            return (Integer) value >= from && (Integer) value <= to
        }
        if (value instanceof Long) {
            return (Long) value >= from && (Long) value <= to
        }
        if (value instanceof BigInteger) {
            final BigInteger bigint = (BigInteger) value
            return bigint.compareTo(BigInteger.valueOf(from)) >= 0 &&
                    bigint.compareTo(BigInteger.valueOf(to)) <= 0
        }
        if (value instanceof String) {
            // check if is expression, if yes just pass it as string with out validation
            if(value.startsWith('${') && value.endsWith('}')) {
                return true
            } else {
                Long convertedValue = value as Long

                return convertedValue >= from && convertedValue <= to
            }
        }

        return false
    }
}
