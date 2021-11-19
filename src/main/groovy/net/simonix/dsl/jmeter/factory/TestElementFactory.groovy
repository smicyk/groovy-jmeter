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
package net.simonix.dsl.jmeter.factory

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import net.simonix.dsl.jmeter.validation.PropertyValidator
import net.simonix.dsl.jmeter.validation.Validator
import org.apache.jmeter.testelement.TestElement

/**
 * Factory for {@link TestElement}.
 *
 * Used for test elements which are leaves and usually belong to some grouping element.
 *
 * They don't have GUI class.
 */
@CompileDynamic
class TestElementFactory extends AbstractTestElementFactory {

    final Class testElementClass
    final boolean leaf

    final KeywordDefinition definition
    final Validator validator

    protected TestElementFactory(Class testElementClass, KeywordDefinition definition) {
        this(testElementClass, definition.leaf, definition)
    }

    protected TestElementFactory(Class testElementClass, boolean leaf, KeywordDefinition definition) {
        this.testElementClass = testElementClass
        this.leaf = leaf
        this.definition = definition

        this.validator = new PropertyValidator(definition.properties, definition.valueIsProperty)
    }

    boolean isLeaf() {
        return leaf
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        return (TestElement) testElementClass.newInstance()
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        // empty implementation
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // empty implementation
    }

    void updateOnComplete(Object parent, Object child) {
        // default implementation is empty
    }
}
