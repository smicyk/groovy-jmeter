/*
 * Copyright 2021 Szymon Micyk
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
package net.simonix.dsl.jmeter.model.definition


import net.simonix.dsl.jmeter.model.constraint.PropertyConstraint
import org.codehaus.groovy.runtime.InvokerHelper

class DefinitionBuilder {

    static final Properties messages = new Properties();

    static {
        DefinitionBuilder.class.getResourceAsStream("messages.properties").withReader {
            messages.load(it)
        }
    }

    static KeywordDefinition keyword(String name, KeywordCategory category) {
        String title = messages."${name}.title"
        String description = messages."${name}.description"

        KeywordBuilder builder = new KeywordBuilder(name, category, title, description)

        return builder.build()
    }

    static KeywordDefinition keyword(String name, KeywordCategory category, Closure c) {
        String title = messages."${name}.title"
        String description = messages."${name}.description"

        c.delegate = new KeywordBuilder(name, category, title, description)

        KeywordBuilder builder = InvokerHelper.invokeClosure(c, []) as KeywordBuilder

        return builder.build()
    }

    static Set<PropertyDefinition> properties(Closure c) {
        c.delegate = new PropertyBuilder()

        PropertyBuilder builder = InvokerHelper.invokeClosure(c, []) as PropertyBuilder

        return builder.build()
    }

    static class KeywordBuilder {
        String name
        KeywordCategory category
        String title
        String description
        Set<PropertyDefinition> properties

        KeywordBuilder(String name, KeywordCategory category, String title, String description) {
            this.name = name
            this.title = title
            this.category = category
            this.description = description
            this.properties = [] as Set<PropertyDefinition>
        }

        KeywordBuilder property(Map config) {
            PropertyDefinition propertyDefinition = propertyImpl(config)
            propertyDefinition.title = messages."${name}.property.${propertyDefinition.name}.title"
            propertyDefinition.description = messages."${name}.property.${propertyDefinition.name}.description"

            this.properties.add(propertyDefinition)

            return this
        }

        KeywordBuilder include(Set<PropertyDefinition> properties) {
            assert properties != null

            properties.each {propertyDefinition ->
                propertyDefinition.title = messages."${name}.property.${propertyDefinition.name}.title"
                propertyDefinition.description = messages."${name}.property.${propertyDefinition.name}.description"

                this.properties.add(propertyDefinition)
            }
            this.properties.addAll(properties)

            return this
        }

        KeywordDefinition build() {
            return new KeywordDefinition(name, category, title, description, properties)
        }
    }

    static class PropertyBuilder {
        Set<PropertyDefinition> properties

        PropertyBuilder() {
            this.properties = []
        }

        PropertyBuilder property(Map config) {
            properties.add(propertyImpl(config))

            return this
        }

        Set<PropertyDefinition> build() {
            return properties.toSet()
        }
    }

    private static PropertyDefinition propertyImpl(Map config) {
        assert config.name != null

        String name = config.name
        boolean required = config.required ?: false
        PropertyConstraint constraints = (config.constraints ?: null) as PropertyConstraint
        Object defaultValue = config.defaultValue == null ? null : config.defaultValue
        String separator = config.separator
        Class type = config.type as Class

        PropertyDefinition propertyDefinition = new PropertyDefinition(name: name, required: required, constraints: constraints, defaultValue: defaultValue, separator: separator, type: type)

        return propertyDefinition
    }
}
