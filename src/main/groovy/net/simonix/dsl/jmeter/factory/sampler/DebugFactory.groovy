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
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.sampler.DebugSampler
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class DebugFactory extends TestElementNodeFactory {
    
    DebugFactory(String testElementName) {
        super(testElementName, DebugSampler, TestBeanGUI, false, DslDefinition.DEBUG_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.displayJMeterProperties = readValue(config.displayJMeterProperties, false)
        testElement.displayJMeterVariables = readValue(config.displayJMeterVariables, false)
        testElement.displaySystemProperties = readValue(config.displaySystemProperties, false)
    }
}
