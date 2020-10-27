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

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.handler.PatternHandler
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.assertions.ResponseAssertion
import org.apache.jmeter.assertions.gui.AssertionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_response</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_response (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 *    field: string [<strong>response_data</strong>, response_document, response_code, response_message, response_headers, request_data, request_headers, url]
 *    variable: string
 *    rule: string [<strong>contains</strong>, matches, equals, substring]
 *    message: string
 *    ignoreStatus: boolean [false]
 *    any: boolean [false]
 *    negate: boolean [false]
 * ) {
 *  {@link PatternHandler pattern}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Response_Assertion">Response Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class ResponseAssertionFactory extends TestElementNodeFactory {

    ResponseAssertionFactory(String testElementName) {
        super(testElementName, ResponseAssertion, AssertionGui, false, DslDefinition.ASSERT_RESPONSE_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = readValue(config.applyTo, 'all')
        String variableName = readValue(config.variable, null)
        String field = readValue(config.field, 'response_data')
        String message = readValue(config.message, '')
        String rule = readValue(config.rule, 'contains')
        boolean ignoreStatus = readValue(config.ignoreStatus, false)
        boolean anyMatch = readValue(config.any, false)
        boolean negate = readValue(config.negate, false)

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

        if (field == 'response_data') {
            testElement.setTestFieldResponseData()
        } else if (field == 'response_document') {
            testElement.setTestFieldResponseDataAsDocument()
        } else if (field == 'response_code') {
            testElement.setTestFieldResponseCode()
        } else if (field == 'response_message') {
            testElement.setTestFieldResponseMessage()
        } else if (field == 'response_headers') {
            testElement.setTestFieldResponseHeaders()
        } else if (field == 'request_data') {
            testElement.setTestFieldRequestData()
        } else if (field == 'request_headers') {
            testElement.setTestFieldRequestHeaders()
        } else if (field == 'url') {
            testElement.setTestFieldURL()
        } else {
            testElement.setTestFieldResponseData()
        }

        if (rule == 'contains') {
            testElement.setToContainsType()
        } else if (rule == 'matches') {
            testElement.setToMatchType()
        } else if (rule == 'equals') {
            testElement.setToEqualsType()
        } else if (rule == 'substring') {
            testElement.setToSubstringType()
        } else {
            testElement.setToContainsType()
        }

        testElement.setCustomFailureMessage(message)
        testElement.setAssumeSuccess(ignoreStatus)

        if (anyMatch) {
            testElement.setToOrType()
        } else {
            testElement.unsetOrType()
        }

        if (negate) {
            testElement.setToNotType()
        } else {
            testElement.unsetNotType()
        }
    }

    boolean isHandlesNodeChildren() {
        return true
    }

    boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure c) {
        PatternHandler handler = new PatternHandler(node.testElement)

        def resolver = c.rehydrate(handler, this, this)
        resolver.resolveStrategy = Closure.DELEGATE_ONLY
        resolver()

        return false
    }
}