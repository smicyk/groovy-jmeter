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

import net.simonix.dsl.jmeter.model.PropertyDefinition

class PropertyValidator implements Validator {
    Set<PropertyDefinition> properties = []
    boolean valueIsProperty = false

    PropertyValidator(Set<PropertyDefinition> properties) {
        this.properties.addAll(properties)
    }

    void addProperties(Set<PropertyDefinition> properties) {
        this.properties.addAll(properties)
    }

    ValidationResult validate(Object name, Object value, Map config) {
        Set<String> configKeys = config.collect { it.key } as Set<String>
        Set<String> validKeys = properties.collect {it.name } as Set<String>
        configKeys.removeAll(validKeys)

        if(!configKeys.isEmpty()) {
            return ValidationResult.notValidProperties(name, configKeys, validKeys)
        }

        configKeys = config.collect { it.key } as Set<String>
        Set<String> requiredKeys = properties.findAll {it.required }.collect { it.name } as Set<String>

        if(!configKeys.containsAll(requiredKeys)) {
            if(!valueIsProperty || value == null) {
                return ValidationResult.missingRequiredProperties(name, requiredKeys)
            }
        }

        return ValidationResult.success()
    }
}