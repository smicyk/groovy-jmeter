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
import net.simonix.dsl.jmeter.factory.AbstractJSR223Factory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.timers.JSR223Timer

/**
 * The factory class responsible for building <code>jsrtimer</code> element in the test.
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSR223_Timer">JSR223 Timer</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 * @see AbstractJSR223Factory AbstractJSR223Factory
 */
@CompileDynamic
final class JSR223TimerFactory extends AbstractJSR223Factory {

    JSR223TimerFactory(String testElementName) {
        super(testElementName, JSR223Timer, TestBeanGUI, true)
    }
}
