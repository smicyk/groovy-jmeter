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
package net.simonix.dsl.jmeter.factory.controller

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.LoopController
import org.apache.jmeter.control.gui.LoopControlPanel
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue
import static net.simonix.dsl.jmeter.utils.ConfigUtils.hasValue

/**
 * The factory class responsible for building <code>loop</code> element in the test.
 *
 * <pre>
 * // element structure
 * loop (
 *     count: integer [<strong>1</strong>]
 *     forever: boolean [<strong>false</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Loop_Controller">Loop Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class LoopFactory extends TestElementNodeFactory {

    LoopFactory(String testElementName) {
        super(DslDefinition.LOOP.title, LoopController, LoopControlPanel, false, DslDefinition.LOOP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        Integer count = config.count as Integer

        if (value instanceof Integer) {
            count = value
        }

        if (hasValue(config.forever)) {
            testElement.continueForever = config.forever
        } else {
            testElement.continueForever = false
        }

        // if forever is set to true, we should set count to negative value
        if (testElement.continueForever) {
            testElement.loops = -1
        } else {
            // if it is missing or false we should set it to true but keep the value of count, otherwise it is not consistent with jmeter behaviour
            testElement.loops = count
            testElement.continueForever = true
        }
    }
}
