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
package net.simonix.dsl.jmeter.factory.timer

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.timers.ConstantTimer
import org.apache.jmeter.timers.gui.ConstantTimerGui

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

/**
 * The factory class responsible for building <code>timer</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * timer (
 *     delay: integer   [<strong>300</strong>]
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             timer delay: 300
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Constant_Timer">Constant Timer</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class ConstantTimerFactory extends TestElementNodeFactory {

    ConstantTimerFactory(String testElementName) {
        super(testElementName, ConstantTimer, ConstantTimerGui, true, DslDefinition.CONSTANT_TIMER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.delay = config.delay?.toString() ?: '300'
    }
}
