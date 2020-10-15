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

import org.apache.jmeter.extractor.RegexExtractor
import org.apache.jmeter.extractor.gui.RegexExtractorGui
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>extract_regex</code> element in the test.
 *
 * <pre>
 * // element structure
 * extract_regex (
 *     applyTo: string [<strong>parent</strong>, all, children, variable]
 *     field: string [<strong>response_body</strong>, response_unescaped, response_document, response_headers, response_code, response_message, request_headers, url]
 *     variable: string
 *     expression: string
 *     attribute: string
 *     match: integer [<strong>1</strong>]
 *     useEmptyValue: boolean [<strong>false</strong>]
 *     defaultValue: string
 *     template: string [<strong>$1$</strong>]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Regular_Expression_Extractor">Regular Expression Extractor</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class RegExExtractorFactory extends TestElementNodeFactory {
    
    RegExExtractorFactory(String testElementName) {
        super(testElementName, RegexExtractor, RegexExtractorGui, true)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = readValue(config.applyTo, 'parent')
        String field = readValue(config.field, 'response_body')

        if (applyTo == 'all') {
            testElement.setScopeAll()
        } else if (applyTo == 'parent') {
            testElement.setScopeParent()
        } else if (applyTo == 'children') {
            testElement.setScopeChildren()
        } else if (applyTo == 'variable') {
            testElement.setScopeVariable(variableName)
        } else {
            testElement.setScopeParent()
        }

        if (field == 'response_body') {
            testElement.setUseField(RegexExtractor.USE_BODY)
        } else if (field == 'response_unescaped') {
            testElement.setUseField(RegexExtractor.USE_BODY_UNESCAPED)
        } else if (field == 'response_document') {
            testElement.setUseField(RegexExtractor.USE_BODY_AS_DOCUMENT)
        } else if (field == 'response_headers') {
            testElement.setUseField(RegexExtractor.USE_HDRS)
        } else if (field == 'response_code') {
            testElement.setUseField(RegexExtractor.USE_CODE)
        } else if (field == 'response_message') {
            testElement.setUseField(RegexExtractor.USE_MESSAGE)
        } else if (field == 'request_headers') {
            testElement.setUseField(RegexExtractor.USE_REQUEST_HDRS)
        } else if (field == 'url') {
            testElement.setUseField(RegexExtractor.USE_URL)
        } else {
            testElement.setUseField(RegexExtractor.USE_BODY)
        }

        testElement.defaultEmptyValue = readValue(config.useEmptyValue, false)
        testElement.defaultValue = readValue(config.defaultValue, '')
        testElement.matchNumber = readValue(config.match, 1)
        testElement.refName = readValue(config.variable, '')
        testElement.regex = readValue(config.expression, '')
        testElement.template = readValue(config.template, '\$1\$')
    }
}
