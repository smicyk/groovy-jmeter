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
import net.simonix.dsl.jmeter.model.definition.DefinitionProvider
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.model.definition.DefinitionAwareMap
import net.simonix.dsl.jmeter.validation.ValidatorProvider
import org.apache.jmeter.testelement.TestElement

/**
 * Factory base class for building {@link TestElementNode}.
 */
@CompileDynamic
abstract class AbstractTestElementNodeFactory extends AbstractFactory implements ValidatorProvider, DefinitionProvider {

    abstract TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException

    abstract Class getTestElementClass(Object name, Object value, Map config)

    abstract Class getTestElementGuiClass(Object name, Object value, Map config)

    abstract String getTestElementName(Object name, Object value, Map config)

    abstract void updateCommonProperties(TestElement testElement, Object name, Object value, Map config)

    abstract void updateClassProperties(TestElement testElement, Class testClass, Class testGuiClass)

    abstract void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config)

    abstract void updateOnComplete(Object parent, Object child)

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Map definitionAwareConfig = new DefinitionAwareMap(config, definition)

        TestElement testElement = newTestElement(builder, name, value, definitionAwareConfig)

        updateCommonProperties(testElement, name, value, definitionAwareConfig)
        updateClassProperties(testElement, getTestElementClass(name, value, definitionAwareConfig), getTestElementGuiClass(name, value, definitionAwareConfig))

        updateTestElementProperties(testElement, name, value, definitionAwareConfig)

        return new TestElementNode(name as String, testElement)
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof TestElementNode && child instanceof TestElementNode) {
            parent.add(child)
        }
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config) {
        return false
    }
}
