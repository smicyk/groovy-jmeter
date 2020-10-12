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
package net.simonix.dsl.jmeter.factory.config


import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.gui.ArgumentsPanel
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>variables</code> element in the test.
 * <p>
 * It can define user variables in two ways. The short one with version property, and normal with nested {@link VariableFactory}.
 *
 * <pre>
 * // structure with nested argument
 * variables {
 *     {@link VariableFactory variable}
 * }
 *
 * // structure without nested argument
 * variables (
 *    values: map
 * )
 * // example usage
 * start {
 *     plan {
 *         variables values: [
 *             var1: 'value1',
 *             var2: 'value2'
 *         ]
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#User_Defined_Variables">User Define Variables</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see VariableFactory VariableFactory
 */
final class VariablesFactory extends TestElementNodeFactory {

    VariablesFactory(String testElementName) {
        super(testElementName, Arguments, ArgumentsPanel, false)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        Object values = readValue(config.values, [:])

        values.each { k, v ->
            Argument argument = new Argument()

            argument.name = readValue(k, '')
            argument.value = readValue(v, '')
            argument.metaData = '='
            argument.description = ''

            testElement.addArgument(argument)
        }
    }
}
