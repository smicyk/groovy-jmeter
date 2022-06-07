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
package net.simonix.dsl.jmeter.test.sampler

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.KeywordCategory
import org.apache.jmeter.testbeans.gui.TestBeanGUI

import static net.simonix.dsl.jmeter.model.definition.DefinitionBuilder.keyword

/**
 * Used only in tests.
 * 
 * Handles 'log' keyword.
 */
final class LogSamplerFactory extends TestElementNodeFactory {
    
    LogSamplerFactory(String testElementName) {
        super(testElementName, LogSampler, TestBeanGUI, true, keyword('log', KeywordCategory.SAMPLER))
    }
}
