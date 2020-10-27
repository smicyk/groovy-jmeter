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
package net.simonix.dsl.jmeter.factory.sampler

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.sampler.TestAction
import org.apache.jmeter.sampler.gui.TestActionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class FlowControlActionFactory extends TestElementNodeFactory {

    FlowControlActionFactory(String testElementName) {
        super(testElementName, TestAction, TestActionGui, false, DslDefinition.FLOW_CONTROL_ACTION_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.action = readValue(translateAction(config.action), 1)
        testElement.target = readValue(translateTarget(config.target), 0)
        testElement.duration = readValue(config.duration, '0')
    }

    int translateTarget(String target) {
        if(target == 'all') {
            return TestAction.TEST
        } else if(target == 'current') {
            return TestAction.THREAD
        }

        return TestAction.THREAD
    }

    int translateAction(String action) {
        if(action == 'stop') {
            return TestAction.STOP
        } else if(action == 'pause') {
            return TestAction.PAUSE
        } else if(action == 'stop_now') {
            return TestAction.STOP_NOW
        } else if(action == 'restart_next_loop') {
            return TestAction.RESTART_NEXT_LOOP
        } else if(action == 'start_next') {
            return TestAction.START_NEXT_ITERATION_CURRENT_LOOP
        } else if(action == 'break') {
            return TestAction.BREAK_CURRENT_LOOP
        }

        return TestAction.PAUSE
    }
}
