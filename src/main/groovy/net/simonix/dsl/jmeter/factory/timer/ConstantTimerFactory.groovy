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

@CompileDynamic
final class ConstantTimerFactory extends TestElementNodeFactory {
    
    ConstantTimerFactory(String testElementName) {
        super(testElementName, ConstantTimer, ConstantTimerGui, true, DslDefinition.CONSTANT_TIMER_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.delay = config.delay?.toString() ?: '300'
    }
}
