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
import net.simonix.dsl.jmeter.model.definition.PropertyDefinition
import net.simonix.dsl.jmeter.model.constraint.PropertyConstraint

@CompileDynamic
class PropertyValidator implements Validator {

    Map<String, PropertyDefinition> properties = [:]
    boolean valueIsProperty = false

    // cache for later use
    private Set<String> validKeys
    private Set<String> requiredKeys

    PropertyValidator(Set<PropertyDefinition> properties, boolean valueIsProperty) {
        this.valueIsProperty = valueIsProperty
        properties.each {property ->
            this.properties[property.name] = property
        }

        updateCacheValues()
    }

    PropertyValidator(Set<PropertyDefinition> properties) {
        this(properties, false)
    }

    void addProperties(Set<PropertyDefinition> properties) {
        properties.each {property ->
            this.properties[property.name] = property
        }

        updateCacheValues()
    }

    ValidationResult validate(Object name, Object value, Map config) {
        Set<String> configKeys = config.collect { it.key } as Set<String>
        configKeys.removeAll(validKeys)

        if (!configKeys.isEmpty()) {
            return ValidationResult.notValidProperties(name, configKeys, validKeys)
        }

        configKeys = config.collect { it.key } as Set<String>

        if (!configKeys.containsAll(requiredKeys)) {
            if (!valueIsProperty || value == null) {
                return ValidationResult.missingRequiredProperties(name, requiredKeys)
            }
        }

        def propertyNames = config.find {attribute ->
            PropertyDefinition propertyDefinition = properties[attribute.key]

            if (propertyDefinition?.constraints) {
                PropertyConstraint constraints = propertyDefinition.constraints

                if(!constraints.matches(attribute.value)) {
                    return true
                }
            }

            return false
        }.collect { it.key }

        if(propertyNames) {
            PropertyDefinition property = properties[propertyNames[0]]
            return ValidationResult.notValidValue(name, property.name, property.constraints.description())
        }

        return ValidationResult.success()
    }

    private void updateCacheValues() {
        validKeys = properties.collect { it.key } as Set<String>
        requiredKeys = properties.findAll { it.value.required }.collect { it.key } as Set<String>
    }
}
