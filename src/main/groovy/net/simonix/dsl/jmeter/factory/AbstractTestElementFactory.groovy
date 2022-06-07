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
package net.simonix.dsl.jmeter.factory

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.DefinitionAwareMap
import net.simonix.dsl.jmeter.model.definition.DefinitionProvider
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.validation.ValidatorProvider
import org.apache.jmeter.testelement.TestElement

/**
 * Base class for building {@link TestElement}
 */
@CompileDynamic
abstract class AbstractTestElementFactory extends AbstractFactory implements ValidatorProvider, DefinitionProvider {

    /**
     * Main method for building new {@link TestElement}. All subclasses should override this method.
     *
     * @param builder builder
     * @param name test element name (usually part of DSL keyword)
     * @param value value for element
     * @param config keeps everything except value
     * @return new test element
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    abstract TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException

    abstract void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child)

    abstract void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config)

    abstract void updateOnComplete(Object parent, Object child)

    boolean isLeaf() {
        return true
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Map definitionAwareConfig = new DefinitionAwareMap(config, definition)

        TestElement testElement = newTestElement(builder, name, value, definitionAwareConfig)

        updateTestElementProperties(testElement, name, value, definitionAwareConfig)

        return testElement
    }

    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof TestElementNode) {
            updateParentProperties(builder, parent.testElement, child)
        } else if (parent instanceof TestElement) {
            updateParentProperties(builder, parent, child)
        }
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config) {
        return false
    }
}
