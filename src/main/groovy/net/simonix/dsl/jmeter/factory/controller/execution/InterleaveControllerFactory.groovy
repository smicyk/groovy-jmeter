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

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import org.apache.jmeter.control.InterleaveControl
import org.apache.jmeter.control.gui.InterleaveControlGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>execute_interleave</code> element in the test.
 *
 * <pre>
 * // element structure
 * execute_interleave (
 *     ignore: boolean [<strong>false</strong>]
 *     acrossUsers: boolean [<strong>false</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Interleave_Controller">Interleave Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see net.simonix.dsl.jmeter.factory.controller.ExecuteFactory ExecuteFactory
 */
final class InterleaveControllerFactory extends TestElementNodeFactory {

    InterleaveControllerFactory() {
        super('Interleave Controller', InterleaveControl, InterleaveControlGui, false)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean ignoreSubControllers = readValue(config.ignore, false)

        testElement.style = ignoreSubControllers ? InterleaveControl.IGNORE_SUB_CONTROLLERS : InterleaveControl.USE_SUB_CONTROLLERS
        testElement.interleaveAccrossThreads = readValue(config.acrossUsers, false)
    }
}
