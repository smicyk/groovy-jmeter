/*
 * Copyright 2019 Szymon Micyk
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
import net.simonix.dsl.jmeter.model.PropertyDefinition
import net.simonix.dsl.jmeter.validation.PropertyValidator
import org.apache.jmeter.testelement.TestElement

/**
 * Factory for {@link TestElement}.
 * It is used for test elements which are leaves and usually belong to some grouping element.
 * They don't have GUI class.
 */
@CompileDynamic
class TestElementFactory extends AbstractTestElementFactory {

    final Class testElementClass
    final boolean leaf

    final PropertyValidator validator

    protected TestElementFactory(Class testElementClass, Set<PropertyDefinition> properties) {
        this(testElementClass, true)

        this.validator.addProperties(properties)
    }

    protected TestElementFactory(Class testElementClass, boolean leaf, Set<PropertyDefinition> properties) {
        this(testElementClass, leaf)

        this.validator.addProperties(properties)
    }

    protected TestElementFactory(Class testElementClass, boolean leaf) {
        this.testElementClass = testElementClass
        this.leaf = leaf

        this.validator = new PropertyValidator([] as Set<PropertyDefinition>)
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
}
