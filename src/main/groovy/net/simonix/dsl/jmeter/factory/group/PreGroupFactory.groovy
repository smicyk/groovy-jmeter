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
package net.simonix.dsl.jmeter.factory.group;

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.LoopController
import org.apache.jmeter.control.gui.LoopControlPanel
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.threads.AbstractThreadGroup
import org.apache.jmeter.threads.SetupThreadGroup
import org.apache.jmeter.threads.ThreadGroup
import org.apache.jmeter.threads.gui.SetupThreadGroupGui

/**
 * The factory class responsible for building <code>before</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * before (
 *   users: integer        [<strong>1</strong>]
 *   rampUp: integer       [<strong>1</strong>]
 *   // properties for scheduler
 *   scheduler: boolean    [<strong>false</strong>]
 *   delay: integer        [<strong>0</strong>]
 *   duration: integer     [<strong>0</strong>]
 *   // properties for loop
 *   loops: integer        [<strong>1</strong>]
 *   forever: boolean      [<strong>false</strong>]
 *   onError: string       [<strong>continue</strong>, start_next, stop_user, stop_test, stop_now]
 * ) {
 *   groups | controllers | samplers | config
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         before(users: 10, rampUp: 60) {
 *             // other configurations
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#setUp_Thread_Group">setUp Thread Group</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class PreGroupFactory extends TestElementNodeFactory {

    PreGroupFactory() {
        super(SetupThreadGroup, SetupThreadGroupGui, DslDefinition.BEFORE_GROUP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.setProperty(AbstractThreadGroup.ON_SAMPLE_ERROR, mapOnError(config.onError))

        testElement.numThreads = config.users as Integer
        testElement.rampUp = config.rampUp as Integer
        testElement.isSameUserOnNextIteration = config.keepUser

        // scheduler configuration
        testElement.scheduler = config.scheduler
        testElement.delay = config.delay
        testElement.duration = config.duration

        // set default controller as loop (that seems to be jmeter defaults)
        LoopController defaultLoopController = new LoopController()
        defaultLoopController.loops = config.loops
        defaultLoopController.continueForever = config.forever
        defaultLoopController.setEnabled(true)
        defaultLoopController.setProperty(TestElement.TEST_CLASS, LoopController.name)
        defaultLoopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.name)

        testElement.samplerController  = defaultLoopController
    }

    String mapOnError(value) {
        if(value == 'continue') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_CONTINUE
        } else if(value == 'start_next') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_START_NEXT_LOOP
        } else if(value == 'stop_user') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTHREAD
        } else if(value == 'stop_test') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTEST
        } else if(value == 'stop_now') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTEST_NOW
        } else {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_CONTINUE
        }
    }
}
