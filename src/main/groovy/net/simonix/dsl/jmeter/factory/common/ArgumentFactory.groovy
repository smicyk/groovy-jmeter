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
package net.simonix.dsl.jmeter.factory.common


import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Builds the single <code>argument</code> for test element. It is used with conjunction with <code>arguments</code>.
 *
 * <pre>
 * // structure of the argument
 * argument (
 *     name: string
 *     value: string
 * )
 * // example usage
 * start {
 *     plan {
 *         arguments {
 *             argument(name: 'var1', value: 'value1')
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see ArgumentsFactory ArgumentsFactory
 * @see net.simonix.dsl.jmeter.factory.plan.PlanFactory PlanFactory
 * @see net.simonix.dsl.jmeter.factory.listener.BackendListenerFactory BackendListenerFactory
 */
final class ArgumentFactory extends TestElementFactory {

    ArgumentFactory() {
        super(Argument, DslDefinition.ARGUMENT_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.name = readValue(config.name, '')
        testElement.value = readValue(config.value, '')
    }

    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof Arguments && child instanceof Argument) {
            parent.addArgument(child)
        }
    }
}
