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
final class InListPropertyConstraint implements PropertyConstraint {

    List<String> values

    @Override
    boolean matches(Object value) {
        if(value instanceof String) {
            // check if is expression, if yes just pass it as string with out validation
            if(value.startsWith('${') && value.endsWith('}')) {
                return true
            }
        }

        return values.contains(value)
    }

    @Override
    String description() {
        return "${values}"
    }
}
