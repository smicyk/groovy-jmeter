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

import net.simonix.dsl.jmeter.model.definition.PropertyDefinition
import spock.lang.Specification

import static net.simonix.dsl.jmeter.model.definition.DefinitionBuilder.properties
import static net.simonix.dsl.jmeter.model.constraint.Constraints.inList
import static net.simonix.dsl.jmeter.model.constraint.Constraints.range

class PropertyValidatorSpec extends Specification {

    static final Set<PropertyDefinition> TEST_PROPERTIES = properties {
        property(name: 'name', required: false)
        property(name: 'comments', required: false)
        property(name: 'enabled', required: true)
        property(name: 'mode', required: false, constraints: inList(['value1', 'value2']))
        property(name: 'counter', required: false, constraints: range(10, 20))
    }

    def "All fields are correct"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)

        when:
        ValidationResult result = validator.validate('test', null, [name: 'test', comments: 'comments', enabled: true, mode: 'value2', counter: 15])

        then:
        result == ValidationResult.success()
    }

    def "Not valid property"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)

        when:
        ValidationResult result = validator.validate('test', null, [property: 'test'])

        then:
        result == ValidationResult.notValidProperties('test', [property: 'test'].collect { it.key } as Set<String>, TEST_PROPERTIES.collect {it.name } as Set<String>)
    }

    def "Required property missing"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)

        when:
        ValidationResult result = validator.validate('test', null, [name: 'test', comments: 'comments'])

        then:
        result == ValidationResult.missingRequiredProperties('test', TEST_PROPERTIES.findAll { it.required }.collect { it.name } as Set<String>)
    }

    def "Required property missing where value is property"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)
        validator.valueIsProperty = true

        when:
        ValidationResult result = validator.validate('test', 'value', [name: 'test', comments: 'comments'])

        then:
        result == ValidationResult.success()
    }

    def "Property value not valid (inList)"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)

        when:
        ValidationResult result = validator.validate('test', 'value', [name: 'test', comments: 'comments', enabled: true, mode: 'badValue'])

        then:
        result == ValidationResult.notValidValue('test', 'mode', ['value1', 'value2'].toString())
    }

    def "Property value not valid (range)"() {
        given: 'property validator with test properties'

        PropertyValidator validator = new PropertyValidator(TEST_PROPERTIES)

        when:
        ValidationResult result = validator.validate('test', 'value', [name: 'test', comments: 'comments', enabled: true, counter: 8])

        then:
        result == ValidationResult.notValidValue('test', 'counter','range 10..20')
    }
}
