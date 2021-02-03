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

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import net.simonix.dsl.jmeter.model.definition.PropertyDefinition

@CompileDynamic
class DefinitionAwareMap extends HashMap<String, Object> implements Map<String, Object> {

    KeywordDefinition definition
    Map<String, PropertyDefinition> properties

    DefinitionAwareMap(Map<String, Object> m, KeywordDefinition definition) {
        super(m)

        this.definition = definition
        this.properties = this.definition.properties.collectEntries {property -> [property.name, property] }
    }

    @Override
    Object get(Object key) {
        Object value = super.get(key)

        if(value == null) {
            PropertyDefinition property = properties.get(key)

            if(property != null) {
                value = property.defaultValue
            }
        }

        if(value != null) {
            if (List.isAssignableFrom(value.getClass())) {
                PropertyDefinition property = properties.get(key)

                return value.join(property.separator)
            }
        }

        return value
    }

    Object getRaw(String key) {
        return super.get(key)
    }
}
