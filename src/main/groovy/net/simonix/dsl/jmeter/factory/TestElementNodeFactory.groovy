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
import net.simonix.dsl.jmeter.model.DslDefinition
import net.simonix.dsl.jmeter.validation.PropertyValidator
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Common implementation for handling creation of {@link net.simonix.dsl.jmeter.model.TestElementNode}.
 * <p>
 * All elements has three common properties <code>name, comment and enabled</code>
 *
 * <pre>
 * // common properties structure
 * element (
 *     name: string
 *     comment: string
 *     enabled: boolean
 * ) {
 *     elements
 * }
 * </pre>
 */
@CompileDynamic
class TestElementNodeFactory extends AbstractTestElementNodeFactory {

    final Class testElementClass
    final Class testElementGuiClass
    final String testElementName
    final boolean leaf

    final PropertyValidator validator

    protected TestElementNodeFactory(String testElementName, Class testElementClass, Class testElementGuiClass, boolean leaf, Set<PropertyDefinition> properties) {
        this(testElementName, testElementClass, testElementGuiClass, leaf)

        this.validator.addProperties(properties)
    }

    protected TestElementNodeFactory(String testElementName, Class testElementClass, Class testElementGuiClass, boolean leaf) {
        this.testElementClass = testElementClass
        this.testElementGuiClass = testElementGuiClass
        this.testElementName = testElementName
        this.leaf = leaf

        this.validator = new PropertyValidator(DslDefinition.COMMON_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // default implementation is empty
    }

    boolean isLeaf() {
        return leaf
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        return (TestElement) testElementClass.newInstance()
    }

    Class getTestElementClass(Object name, Object value, Map config) {
        return testElementClass
    }

    Class getTestElementGuiClass(Object name, Object value, Map config) {
        return testElementGuiClass
    }

    String getTestElementName(Object name, Object value, Map config) {
        if (value instanceof String) {
            return value ?: config.name?.toString() ?: testElementName
        }

        return config.name?.toString() ?: testElementName
    }

    void updateCommonProperties(TestElement testElement, Object name, Object value, Map config) {
        if (testElement != null) {
            testElement.name = getTestElementName(name, value, config)
            testElement.comment = readValue(config.comments, '')
            testElement.enabled = readValue(config.enabled, true)
        }
    }

    void updateClassProperties(TestElement testElement, Class testClass, Class testGuiClass) {
        if (testElement != null) {
            testElement.setProperty(TestElement.TEST_CLASS, testClass.name)
            testElement.setProperty(TestElement.GUI_CLASS, testGuiClass.name)
        }
    }
}
