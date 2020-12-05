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
package net.simonix.dsl.jmeter.factory.controller.execution

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.control.IfController
import org.apache.jmeter.control.gui.IfControllerPanel
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>execute_if</code> element in the test.
 *
 * <pre>
 * // element structure
 * execute_if (
 *     condition: string
 *     useExpression: boolean [<strong>true</strong>]
 *     evaluateAll: boolean [<strong>false</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * // example usage
 * start {
 *     group {
 *         execute(type: 'if', condition: '__jexl3(${var_condition} == 5)')
 *     }
 * }
 * // or
 * start {
 *     group {
 *         execute_if('__jexl3(${var_condition} == 5)') {
 *
 *         }
 *         execute_if(condition: '__jexl3(${var_condition} == 5)') {
 *
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#If_Controller">If Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see net.simonix.dsl.jmeter.factory.controller.ExecuteFactory ExecuteFactory
 */
@CompileDynamic
final class IfControllerFactory extends TestElementNodeFactory {

    IfControllerFactory() {
        super('If Controller', IfController, IfControllerPanel, false, DslDefinition.EXECUTE_IF)

        this.validator.valueIsProperty = true
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String condition = readValue(value, readValue(config.condition, ''))

        boolean useExpression = readValue(config.useExpression, true)
        boolean evaluateAll = readValue(config.evaluateAll, false)

        if (useExpression) {
            testElement.condition = "\${${condition}}"
        } else {
            testElement.condition = condition
        }

        testElement.evaluateAll = evaluateAll
        testElement.useExpression = useExpression
    }
}
