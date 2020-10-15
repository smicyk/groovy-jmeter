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
package net.simonix.dsl.jmeter.factory.extractor

import org.apache.jmeter.extractor.HtmlExtractor
import org.apache.jmeter.extractor.gui.HtmlExtractorGui
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>extract_css</code> element in the test.
 *
 * <pre>
 * // element structure
 * extract_css (
 *     applyTo: string [<strong>parent</strong>, all, children, variable]
 *     variable: string
 *     expression: string
 *     attribute: string
 *     match: integer [<strong>0</strong>]
 *     useEmptyValue: boolean [<strong>false</strong>]
 *     defaultValue: string
 *     attribute: string
 *     engine: enum [<strong>JSOUP</strong>, JODD]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#CSS_Selector_Extractor">CSS Selector Extractor</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class CssSelectorExtractorFactory extends TestElementNodeFactory {
    
    CssSelectorExtractorFactory(String testElementName) {
        super(testElementName, HtmlExtractor, HtmlExtractorGui, true)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = readValue(config.applyTo, 'parent')

        if (applyTo == 'all') {
            testElement.setScopeAll()
        } else if (applyTo == 'parent') {
            testElement.setScopeParent()
        } else if (applyTo == 'children') {
            testElement.setScopeChildren()
        } else if (applyTo == 'variable') {
            testElement.setScopeVariable(variableName)
        } else {
            testElement.setScopeAll()
        }

        testElement.defaultEmptyValue = readValue(config.useEmptyValue, false)
        testElement.defaultValue = readValue(config.defaultValue, '')
        testElement.matchNumber = readValue(config.match, 0)
        testElement.refName = readValue(config.variable, '')

        testElement.expression = readValue(config.expression, '')
        testElement.attribute = readValue(config.attribute, '')
        testElement.extractor = readValue(config.engine, HtmlExtractor.EXTRACTOR_JSOUP)
    }
}
