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
        return keyword(name, category, '')
    }

    static KeywordDefinition keyword(String name, KeywordCategory category, String prefix) {
        String title = messages."${prefix}${name}.title"
        String description = messages."${prefix}${name}.description"
        String component = messages."${prefix}${name}.component"

        KeywordBuilder builder = new KeywordBuilder(name, title, description, component, category, prefix)

        return builder.build()
    }

    static KeywordDefinition keyword(String name, KeywordCategory category, Closure c) {
        return keyword(name, category, '', c)
    }

    static KeywordDefinition keyword(String name, KeywordCategory category, String prefix, Closure c) {
        String title = messages."${prefix}${name}.title"
        String description = messages."${prefix}${name}.description"
        String component = messages."${prefix}${name}.component"

        KeywordBuilder builder = new KeywordBuilder(name, title, description, component, category, prefix)

        c.delegate = builder

        builder = InvokerHelper.invokeClosure(c, []) as KeywordBuilder

        return builder.build()
    }

    static Set<PropertyDefinition> properties(Closure c) {
        c.delegate = new PropertyBuilder()

        PropertyBuilder builder = InvokerHelper.invokeClosure(c, []) as PropertyBuilder

        return builder.build()
    }

    static class KeywordBuilder {

        String name
        String prefix
        KeywordCategory category
        String title
        String description
        String component
        boolean leaf = false
        boolean valueIsProperty = false
        Map<String, PropertyDefinition> properties

        KeywordBuilder(String name, String title, String description, String component, KeywordCategory category, String prefix = '') {
            this.name = name
            this.title = title
            this.description = description
            this.component = component
            this.prefix = prefix
            this.category = category
            this.properties = [:] as LinkedHashMap<String, PropertyDefinition>
        }

        KeywordBuilder property(Map config) {
            assert config.name != null

            String title = messages."${prefix}${name}.property.${config.name}.title" as String
            String description = messages."${prefix}${name}.property.${config.name}.description" as String

            PropertyDefinition propertyDefinition = propertyImpl(config, title, description)

            this.properties.put(propertyDefinition.name, propertyDefinition)

            return this
        }

        KeywordBuilder override(Map config) {
            assert config.name != null

            PropertyDefinition propertyDefinition = this.properties.get(config.name)

            if (propertyDefinition) {
                this.properties.put(propertyDefinition.name, new PropertyDefinition(
                        name: propertyDefinition.name,
                        title:  propertyDefinition.title,
                        description: propertyDefinition.description,
                        type: config.type != null ? config.type as Class : propertyDefinition.type,
                        required: config.required != null ? config.required : propertyDefinition.required,
                        defaultValue: config.defaultValue != null ? config.defaultValue : propertyDefinition.defaultValue,
                        separator: config.separator != null ? config.separator : propertyDefinition.separator,
                        constraints: config.constraints != null ? config.constraints : propertyDefinition.constraints
                ))
            }

            return this
        }

        KeywordBuilder include(Set<PropertyDefinition> properties) {
            assert properties != null

            properties.each { propertyDefinition ->
                this.properties.put(propertyDefinition.name, new PropertyDefinition(
                        name: propertyDefinition.name,
                        title: messages."${prefix}${name}.property.${propertyDefinition.name}.title" as String,
                        description: messages."${prefix}${name}.property.${propertyDefinition.name}.description" as String,
                        type: propertyDefinition.type,
                        required:  propertyDefinition.required,
                        defaultValue: propertyDefinition.defaultValue,
                        separator: propertyDefinition.separator,
                        constraints: propertyDefinition.constraints
                ))
            }

            return this
        }

        KeywordBuilder leaf() {
            this.leaf = true

            return this
        }

        KeywordBuilder valueIsProperty() {
            this.valueIsProperty = true

            return this
        }

        KeywordDefinition build() {
            return new KeywordDefinition(
                    name: name,
                    prefix: prefix,
                    category: category,
                    title: title,
                    description: description,
                    component: component,
                    leaf: leaf,
                    valueIsProperty: valueIsProperty,
                    properties: properties.collect([] as LinkedHashSet<PropertyDefinition>) {
                        entry -> entry.value
                    }
            )
        }
    }

    static class PropertyBuilder {

        Set<PropertyDefinition> properties

        PropertyBuilder() {
            this.properties = [] as LinkedHashSet<PropertyDefinition>
        }

        PropertyBuilder property(Map config) {
            properties.add(propertyImpl(config))

            return this
        }

        Set<PropertyDefinition> build() {
            return new LinkedHashSet<PropertyDefinition>(properties)
        }
    }

    private static PropertyDefinition propertyImpl(Map config, String title = '', String description = '') {
        assert config.name != null

        String name = config.name
        boolean required = config.required ?: false
        PropertyConstraint constraints = (config.constraints ?: null) as PropertyConstraint
        Object defaultValue = config.defaultValue == null ? null : config.defaultValue
        String separator = config.separator
        Class type = config.type as Class

        PropertyDefinition propertyDefinition = new PropertyDefinition(name: name, title: title, description: description, required: required, constraints: constraints, defaultValue: defaultValue, separator: separator, type: type)

        return propertyDefinition
    }
}
