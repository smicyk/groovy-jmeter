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
package net.simonix.dsl.jmeter.factory.timer

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.timers.SyncTimer

/**
 * The factory class responsible for building <code>synchronizing_timer</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * synchronizing_timer (
 *     users: integer   [<strong>3</strong>]
 *     timeout: long    [<strong>0</strong>]
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             synchronizing_timer users: 10, timeout: 1000
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Synchronizing_Timer">Synchronizing Timer</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class SynchronizingTimerFactory extends TestElementNodeFactory {

    SynchronizingTimerFactory() {
        super(SyncTimer, TestBeanGUI, DslDefinition.SYNCHRONIZING_TIMER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.setProperty('groupSize', config.users)
        testElement.setProperty('timeoutInMs', config.timeout)
    }
}
