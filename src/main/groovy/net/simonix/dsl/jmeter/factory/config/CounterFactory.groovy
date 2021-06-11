/*
 * Copyright 2021 Szymon Micyk
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
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.modifiers.CounterConfig
import org.apache.jmeter.modifiers.gui.CounterConfigGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>counter</code> element in the test.
 *
 * <pre>
 * // element structure
 * counter (
 *   perUser: boolean [<strong>false</strong>]
 *   reset: boolean [<strong>false</strong>]
 *   start: integer [<strong>0</strong>]
 *   end: integer [<strong>max</strong>]
 *   increment: integer [<strong>1</strong>]
 *   variable: string [<strong>c</strong>]
 *   format: string
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             counter(perUser: true, reset: true, start: 1, end: 10, increment: 2, variable: 'd', format: '0.0')
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Counter">Counter</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class CounterFactory extends TestElementNodeFactory {

    CounterFactory() {
        super(CounterConfig, CounterConfigGui, DslDefinition.COUNTER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean perUser = config.perUser
        boolean reset = config.reset

        testElement.start = config.start
        testElement.end = config.end
        testElement.increment = config.increment
        testElement.varName = config.variable
        testElement.format = config.format
        testElement.isPerUser = perUser
        testElement.resetOnThreadGroupIteration = reset
    }
}
