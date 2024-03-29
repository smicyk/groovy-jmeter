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
package net.simonix.dsl.jmeter.factory.extractor

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.extractor.XPath2Extractor
import org.apache.jmeter.extractor.gui.XPath2ExtractorGui
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>extract_xpath</code> element in the test.
 *
 * <pre>
 * // element structure
 * extract_xpath (
 *     applyTo: string       [<strong>>parent</strong>, all, children, variable]
 *     defaultValue: string
 *     match: integer        [<strong>0</strong>]
 *     variable: string
 *     expression: string
 *     namespaces: string
 *     fragment: boolean     [<strong>false</strong>]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#XPath2_Extractor">XPath2 Extractor</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class XPathExtractorFactory  extends TestElementNodeFactory {

    XPathExtractorFactory() {
        super(XPath2Extractor, XPath2ExtractorGui, DslDefinition.XPATH_EXTRACTOR)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = config.applyTo

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

        testElement.defaultValue = config.defaultValue
        testElement.matchNumber = config.match
        testElement.refName = config.variable
        testElement.XPathQuery = config.expression
        testElement.namespaces = config.namespaces
        testElement.fragment = config.fragment
    }
}
