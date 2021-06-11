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
package net.simonix.dsl.jmeter.factory.timer

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.timers.UniformRandomTimer
import org.apache.jmeter.timers.gui.UniformRandomTimerGui

/**
 * The factory class responsible for building <code>uniform_timer</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * uniform_timer (
 *     delay: integer   [<strong>1000</strong>]
 *     range: integer   [<strong>100</strong>]
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             uniform_timer delay: 300, range: 1000
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Uniform_Random_Timer">Uniform Random Timer</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class UniformTimerFactory extends TestElementNodeFactory {

    UniformTimerFactory() {
        super(UniformRandomTimer, UniformRandomTimerGui, DslDefinition.UNIFORM_TIMER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.delay = config.delay?.toString()
        testElement.range = config.range
    }
}
