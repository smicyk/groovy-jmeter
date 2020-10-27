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
package net.simonix.dsl.jmeter.factory.assertion

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.assertions.XPathAssertion
import org.apache.jmeter.assertions.gui.XPathAssertionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_xpath</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_response (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 *    variable: string
 *    ignoreWhitespace: boolean [false]
 *    validateXml: boolean [false]
 *    useNamespace: boolean [false]
 *    fetchDtd: boolean [false]
 *    failOnNoMatch: boolean [false]
 *    useTolerant: boolean [false]
 *    reportErrors: boolean [false]
 *    showWarnings: boolean [false]
 *    quiet: boolean [true]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#XPath_Assertion">XPath Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class XPathAssertionFactory extends TestElementNodeFactory {

    XPathAssertionFactory(String testElementName) {
        super(testElementName, XPathAssertion, XPathAssertionGui, true, DslDefinition.ASSERT_XPATH_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = readValue(config.applyTo, 'all')
        String variableName = readValue(config.variable, null)

        String xpath = readValue(config.xpath, '/')
        boolean ignoreWhitespace = readValue(config.ignoreWhitespace, false)
        boolean validateXml = readValue(config.validate, false)
        boolean useNamespace = readValue(config.useNamespace, false)
        boolean fetchDtd = readValue(config.fetchDtd, false)
        boolean failOnNoMatch = readValue(config.failOnNoMatch, false)

        // tidy parser
        boolean useTolerant = readValue(config.useTolerant, false)
        boolean reportErrors = readValue(config.reportErrors, false)
        boolean showWarnings = readValue(config.showWarnings, false)
        boolean quiet = readValue(config.quiet, true)

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

        testElement.setXPathString(xpath)
        testElement.setWhitespace(ignoreWhitespace)
        testElement.setValidating(validateXml)
        testElement.setNamespace(useNamespace)
        testElement.setTolerant(useTolerant)
        testElement.setNegated(failOnNoMatch)
        testElement.setReportErrors(reportErrors)
        testElement.setShowWarnings(showWarnings)
        testElement.setQuiet(quiet)
        testElement.setDownloadDTDs(fetchDtd)
    }
}
