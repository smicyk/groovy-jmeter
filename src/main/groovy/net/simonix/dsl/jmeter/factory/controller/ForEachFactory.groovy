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

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.control.ForeachController
import org.apache.jmeter.control.gui.ForeachControlPanel
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>for_each</code> element in the test.
 *
 * <pre>
 * // element structure
 * for_each (
 *     in: string
 *     out: string
 *     separator: boolean [<strong>true</strong>]
 *     start: integer [<strong>0</strong>]
 *     end: integer [<strong>1</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#ForEach_Controller">ForEach Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class ForEachFactory extends TestElementNodeFactory {

    ForEachFactory(String testElementName) {
        super(testElementName, ForeachController, ForeachControlPanel, false, DslDefinition.FOR_EACH_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.inputVal = readValue(config.in, '')
        testElement.returnVal = readValue(config.out, '')
        testElement.useSeparator = readValue(config.separator, true)
        testElement.startIndex = readValue(config.start, 0)
        testElement.endIndex = readValue(config.end, 1)
    }
}
