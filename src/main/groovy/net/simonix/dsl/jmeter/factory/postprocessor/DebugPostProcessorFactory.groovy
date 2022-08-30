/*
 * Copyright 2022 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.postprocessor

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.extractor.DebugPostProcessor
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

@CompileDynamic
final class DebugPostProcessorFactory extends TestElementNodeFactory {

    DebugPostProcessorFactory() {
        super(DebugPostProcessor, TestBeanGUI, DslDefinition.DEBUG_POSTPROCESSOR)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.displayJMeterProperties = config.displayJMeterProperties
        testElement.displayJMeterVariables = config.displayJMeterVariables
        testElement.displaySystemProperties = config.displaySystemProperties
        testElement.displaySamplerProperties = config.displaySamplerProperties

        testElement.setProperty('displayJMeterProperties', config.displayJMeterProperties)
        testElement.setProperty('displayJMeterVariables', config.displayJMeterVariables)
        testElement.setProperty('displaySystemProperties', config.displaySystemProperties)
        testElement.setProperty('displaySamplerProperties', config.displaySamplerProperties)
    }
}
