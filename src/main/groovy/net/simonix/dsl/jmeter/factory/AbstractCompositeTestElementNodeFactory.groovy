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


import net.simonix.dsl.jmeter.model.TestElementNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Add dynamic creation of {@link TestElementNode}.
 * <p>
 * The real factory should be provided by implementation of {@link AbstractCompositeTestElementNodeFactory#getChildFactory}.
 */
abstract class AbstractCompositeTestElementNodeFactory extends AbstractFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCompositeTestElementNodeFactory)

    /**
     * Get real {@link net.simonix.dsl.jmeter.model.TestElementNode} factory class instead of this one.
     */
    abstract AbstractTestElementNodeFactory getChildFactory(FactoryBuilderSupport builder, Object name, Object value, Map config)

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        AbstractTestElementNodeFactory factory = getChildFactory(builder, name, value, config)

        return factory.newInstance(builder, name, value, config)
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof TestElementNode && child instanceof TestElementNode) {
            parent.add(child)
        }
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config ) {
        return false
    }
}
