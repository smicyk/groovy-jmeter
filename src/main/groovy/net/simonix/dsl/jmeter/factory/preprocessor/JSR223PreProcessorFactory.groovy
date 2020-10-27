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
package net.simonix.dsl.jmeter.factory.preprocessor

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.AbstractJSR223Factory
import org.apache.jmeter.modifiers.JSR223PreProcessor
import org.apache.jmeter.testbeans.gui.TestBeanGUI

/**
 * The factory class responsible for building <code>jsrpreprocessor</code> element in the test.
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSR223_Preprocessor">JSR223 Preprocessor</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 * @see AbstractJSR223Factory AbstractJSR223Factory
 */
@CompileDynamic
final class JSR223PreProcessorFactory extends AbstractJSR223Factory {
    JSR223PreProcessorFactory(String testElementName) {
        super(testElementName, JSR223PreProcessor, TestBeanGUI, true)
    }
}
