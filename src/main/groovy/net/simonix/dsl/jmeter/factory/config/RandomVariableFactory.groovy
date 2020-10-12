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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import org.apache.jmeter.config.RandomVariableConfig
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>random</code> element in the test.
 *
 * <pre>
 * // structure with nested argument
 * random (
 *    minimum: integer [<strong>0</strong>]
 *    maximum: integer [<strong>MAX_INTEGER</strong>]
 *    format: string
 *    variable: string [<strong>r</strong>]
 *    perUser: boolean [<strong>true</strong>]
 *    seed: integer
 * )
 * // example usage
 * start {
 *     plan {
 *         random(minimum: 0, maximum: 100, variable: 'var_random')
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Random_Variable">Random Variable</a>
 *
 */
final class RandomVariableFactory extends TestElementNodeFactory {
    
    RandomVariableFactory(String testElementName) {
        super(testElementName, RandomVariableConfig, TestBeanGUI, true)
    }
    
    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean perUser = readValue(config.perUser, true)

        testElement.minimumValue = readValue(String, config.minimum, 0)
        testElement.maximumValue = readValue(String, config.maximum, Integer.MAX_VALUE)
        testElement.outputFormat = readValue(config.format, null)
        testElement.variableName = readValue(config.variable, 'r')
        testElement.perThread = perUser
        testElement.randomSeed = readValue(config.seed, null)

        testElement.setProperty('minimumValue', testElement.minimumValue)
        testElement.setProperty('maximumValue', testElement.maximumValue)
        testElement.setProperty('outputFormat', testElement.outputFormat)
        testElement.setProperty('perThread', testElement.perThread)
        testElement.setProperty('randomSeed', testElement.randomSeed)
        testElement.setProperty('variableName', testElement.variableName)
    }
}
