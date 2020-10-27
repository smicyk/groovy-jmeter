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

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>variable</code> element in the test.
 * <p>
 * It is used as child element of <pre>variables</pre> test element.
 *
 * <pre>
 * // structure with nested argument
 * variable (
 *    name: string
 *    value: string
 *    description: string
 * )
 * // example usage
 * start {
 *     plan {
 *         variables {
 *             variable(name: 'var1', value: 'value1', description: 'description1')
 *         }
 *     }
 * }
 * </pre>
 * @see VariablesFactory VariablesFactory
 */
@CompileDynamic
final class VariableFactory extends TestElementFactory {

    VariableFactory() {
        super(Argument, DslDefinition.VARIABLE_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.name = readValue(config.name, '')
        testElement.value = readValue(config.value, '')
        testElement.metaData = '='
        testElement.description = readValue(config.description, '')
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof Arguments && child instanceof Argument) {
            parent.addArgument(child)
        }
    }
}
