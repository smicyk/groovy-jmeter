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
package net.simonix.dsl.jmeter.validation

import groovy.transform.CompileDynamic
import groovy.transform.EqualsAndHashCode

@CompileDynamic
@EqualsAndHashCode
final class ValidationResult {

    boolean valid
    String message

    static ValidationResult notValidProperties(Object name, Set<String> configProperties, Set<String> validProperties) {
        return new ValidationResult(valid: false, message: "The keyword '${name}' has invalid properties ${configProperties}. Did you misspell any of the valid properties ${validProperties}?")
    }

    static ValidationResult missingRequiredProperties(Object name, Set<String> requiredProperties) {
        return new ValidationResult(valid: false, message: "The keyword '${name}' is missing required properties. Did you forget to add any of the required properties ${requiredProperties}?")
    }

    static ValidationResult notValidValue(Object name, String property, String values) {
        return new ValidationResult(valid: false, message: "The property '${property}' has invalid value. The value should be from ${values}?")
    }

    static ValidationResult success() {
        return new ValidationResult(valid: true, message: 'Success')
    }
}
