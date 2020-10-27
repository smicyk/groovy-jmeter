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
package net.simonix.dsl.jmeter.factory

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.DslDefinition;
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>jsr</code> elements in the test.
 *
 * <pre>
 * // common structure of the jsr233 elements
 * element (
 *     script: string
 *     cacheKey: boolean
 *     filename: string
 *     parameters: string
 *     language: string
 * )
 * </pre>
 *
 * Available elements:
 * <pre>
 * {@link net.simonix.dsl.jmeter.factory.listener.JSR223ListenerFactory jsrlistener}
 * {@link net.simonix.dsl.jmeter.factory.preprocessor.JSR223PreProcessorFactory jsrpreprocessor}
 * {@link net.simonix.dsl.jmeter.factory.postprocessor.JSR223PostProcessorFactory jsrpostprocessor}
 * {@link net.simonix.dsl.jmeter.factory.assertion.JSR223AssertionFactory jsrassertion}
 * {@link net.simonix.dsl.jmeter.factory.timer.JSR223TimerFactory jsrtimer}
 * {@link net.simonix.dsl.jmeter.factory.sampler.JSR223SamplerFactory jsrsampler}
 * </pre>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
abstract class AbstractJSR223Factory extends TestElementNodeFactory {

    AbstractJSR223Factory(String testElementName, Class testElementClass, Class testElementGuiClass, boolean leaf) {
        super(testElementName, testElementClass, testElementGuiClass, leaf, DslDefinition.JSR223_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String script = readValue(value, readValue(config.script, ''))

        testElement.cacheKey = readValue(config.cacheKey, true)
        testElement.filename = readValue(config.filename, '')
        testElement.parameters = readValue(config.parameters, '')
        testElement.script = script
        testElement.scriptLanguage = readValue(config.language, 'groovy')

        testElement.setProperty('scriptLanguage', readValue(config.language, 'groovy').toString())
        testElement.setProperty('parameters', readValue(config.parameters, '').toString())
        testElement.setProperty('filename', readValue(config.filename, '').toString())
        testElement.setProperty('cacheKey', readValue(config.cacheKey, true).toString())
        testElement.setProperty('script', script)
    }
}
