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
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.assertions.SizeAssertion
import org.apache.jmeter.assertions.gui.SizeAssertionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_size</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_response (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 *    field: string [<strong>response_data</strong>, response_body, response_code, response_message, response_headers]
 *    variable: string
 *    rule: string [<strong>contains</strong>, matches, equals, substring]
 *    size: long
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Size_Assertion">Size Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class SizeAssertionFactory extends TestElementNodeFactory {

    SizeAssertionFactory(String testElementName) {
        super(testElementName, SizeAssertion, SizeAssertionGui, true, DslDefinition.ASSERT_SIZE_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = readValue(config.applyTo, 'all')
        String variableName = readValue(config.variable, null)
        String field = readValue(config.field, 'response_data')
        String rule = readValue(config.rule, 'eq')
        Long size = readValue(Long, config.size, 0)

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
            testElement.setTestFieldNetworkSize()
        } else if (field == 'response_body') {
            testElement.setTestFieldResponseBody()
        } else if (field == 'response_code') {
            testElement.setTestFieldResponseCode()
        } else if (field == 'response_message') {
            testElement.setTestFieldResponseMessage()
        } else if (field == 'response_headers') {
            testElement.setTestFieldResponseHeaders()
        } else {
            testElement.setTestFieldNetworkSize()
        }

        if (rule == 'eq') {
            testElement.setCompOper(SizeAssertion.EQUAL)
        } else if (rule == 'ne') {
            testElement.setCompOper(SizeAssertion.NOTEQUAL)
        } else if (rule == 'lt') {
            testElement.setCompOper(SizeAssertion.LESSTHAN)
        } else if (rule == 'gt') {
            testElement.setCompOper(SizeAssertion.GREATERTHAN)
        } else if (rule == 'le') {
            testElement.setCompOper(SizeAssertion.LESSTHANEQUAL)
        } else if (rule == 'ge') {
            testElement.setCompOper(SizeAssertion.GREATERTHANEQUAL)
        } else {
            testElement.setCompOper(SizeAssertion.EQUAL)
        }

        testElement.setAllowedSize(size)
    }
}