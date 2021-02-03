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

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.TestPlan
import org.apache.jmeter.visualizers.backend.BackendListener

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Builds arguments for test element. It is used with conjunction with <code>plan</code> or <code>backend</code> listener.
 * <p>
 * In <code>plan</code> it works as user define variables. In <code>backend</code> listener as arguments.
 *
 * <pre>
 * // structure with nested argument
 * arguments {
 *     {@link ArgumentFactory argument}
 * }
 *
 * // structure without nested argument
 * arguments (
 *    values: map
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         arguments values: [
 *             'var1': 'value1',
 *             'var2': 'value2'
 *         }
 *     }
 * }
 * </pre>
 *
 * @see ArgumentFactory ArgumentFactory
 */
@CompileDynamic
final class ArgumentsFactory extends TestElementFactory {

    ArgumentsFactory() {
        super(Arguments, false, DslDefinition.ARGUMENTS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        Object values = config.values

        values.each { k, v ->
            Argument argument = new Argument(readValue(String, k, null), readValue(String, v, null))

            testElement.addArgument(argument)
        }
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object arguments) {
        if (arguments instanceof Arguments) {
            if (parent instanceof BackendListener) {
                parent.arguments = arguments
            } else if (parent instanceof TestPlan) {
                parent.userDefinedVariables = arguments
            }
        }
    }
}
