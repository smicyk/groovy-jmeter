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
package net.simonix.dsl.jmeter.factory.group

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.control.LoopController
import org.apache.jmeter.control.gui.LoopControlPanel
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.threads.AbstractThreadGroup
import org.apache.jmeter.threads.ThreadGroup
import org.apache.jmeter.threads.gui.ThreadGroupGui

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>group</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * group (
 *   users: integer        [<strong>1</strong>]
 *   rampUp: integer       [<strong>1</strong>]
 *   delayedStart: boolean [<strong>false</strong>]
 *   // properties for scheduler
 *   scheduler: boolean    [<strong>false</strong>]
 *   delay: integer        [<strong>0</strong>]
 *   duration: integer     [<strong>0</strong>]
 *   // properties for loop
 *   loops: integer        [<strong>1</strong>]
 *   forever: boolean      [<strong>false</strong>]
 * ) {
 *   groups | controllers | samplers | config
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group(users: 10, rampUp: 60) {
 *             // other configurations
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Thread_Group">JMeter Thread Group</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class GroupFactory extends TestElementNodeFactory {

    GroupFactory(String testElementName) {
        super(testElementName, ThreadGroup, ThreadGroupGui, false, DslDefinition.GROUP_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.setProperty(AbstractThreadGroup.ON_SAMPLE_ERROR, AbstractThreadGroup.ON_SAMPLE_ERROR_CONTINUE);

        testElement.numThreads = readValue(Integer, config.users, 1)
        testElement.rampUp = readValue(Integer, config.rampUp, 1)
        testElement.setProperty(ThreadGroup.DELAYED_START, (Boolean) readValue(config.delayedStart, false))

        // scheduler configuration
        testElement.scheduler = readValue(config.scheduler, false)
        testElement.delay = readValue(config.delay, 0)
        testElement.duration = readValue(config.duration, 0)

        // set default controller as loop (that seems to be jmeter defaults)
        LoopController defaultLoopController = new LoopController()
        defaultLoopController.loops = readValue(config.loops, 1)
        defaultLoopController.continueForever = readValue(config.forever, false)
        defaultLoopController.setEnabled(true)
        defaultLoopController.setProperty(TestElement.TEST_CLASS, LoopController.name)
        defaultLoopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.name)

        testElement.samplerController  = defaultLoopController
    }
}
