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
package net.simonix.dsl.jmeter.factory.sampler

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.sampler.DebugSampler
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>debug</code> element in the test.
 *
 * <pre>
 * // element structure
 * debug (
 *     displayJMeterProperties: boolean [<strong>false</strong>]
 *     displayJMeterVariables: boolean  [<strong>false</strong>]
 *     displaySystemProperties: boolean [<strong>false</strong>]
 * ) {
 * }
 *
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Debug_Sampler">Debug Sampler</a>
 *
 * @see BaseHttpFactory BaseHttpFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DebugFactory extends TestElementNodeFactory {

    DebugFactory() {
        super(DebugSampler, TestBeanGUI, DslDefinition.DEBUG)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.displayJMeterProperties = config.displayJMeterProperties
        testElement.displayJMeterVariables = config.displayJMeterVariables
        testElement.displaySystemProperties = config.displaySystemProperties
    }
}
