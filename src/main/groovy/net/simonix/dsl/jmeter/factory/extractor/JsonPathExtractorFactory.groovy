/*
 * Copyright 2020 Szymon Micyk
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


import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor
import org.apache.jmeter.extractor.json.jsonpath.gui.JSONPostProcessorGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValues

/**
 * The factory class responsible for building <code>extract_json</code> element in the test.
 *
 * <pre>
 * // element structure
 * extract_json (
 *     applyTo: string [<strong>parent</strong>, all, children, variable]
 *     variables: string | list
 *     expressions: string | list
 *     matches: integer | list [<strong>1</strong>]
 *     concatenation: boolean [<strong>false</strong>]
 *     defaultValues: string | list
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSON_Extractor">JSON Extractor</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class JsonPathExtractorFactory extends TestElementNodeFactory {

    JsonPathExtractorFactory(String testElementName) {
        super(testElementName, JSONPostProcessor, JSONPostProcessorGui, true)
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
            testElement.setScopeParent()
        }

        testElement.defaultValues = readValues(config.defaultValues, ';', [])
        testElement.matchNumbers = readValues(config.matches, ';', [1])
        testElement.refNames = readValues(config.variables, ';', [])
        testElement.jsonPathExpressions = readValues(config.expressions, ';', [])
        testElement.computeConcatenation = readValue(config.concatenation, false)
    }
}
