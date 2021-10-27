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
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
@CompileDynamic
class KeywordDefinition {

    final String name
    final String prefix
    final String title
    final String description
    final String category
    final boolean leaf
    final boolean valueIsProperty
    final Set<PropertyDefinition> properties

    KeywordDefinition(String name, String prefix, KeywordCategory category, String title, String description, boolean leaf, boolean valueIsProperty, Set<PropertyDefinition> properties) {
        this.name = name
        this.prefix = prefix
        this.category = category
        this.title = title
        this.description = description
        this.leaf = leaf
        this.valueIsProperty = valueIsProperty
        this.properties = properties.asImmutable()
    }
}
