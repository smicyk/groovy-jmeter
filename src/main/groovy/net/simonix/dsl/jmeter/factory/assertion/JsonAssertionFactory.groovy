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
import org.apache.jmeter.assertions.JSONPathAssertion
import org.apache.jmeter.assertions.gui.JSONPathAssertionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_json</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_json (
 *    jpath: string [<strong>$.<strong>]
 *    assert_value: boolean [false]
 *    assert_as_regex: boolean [true]
 *    value: string
 *    expect_null: boolean [false]
 *    invert: boolean [false]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSON_Assertion">JSON Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class JsonAssertionFactory extends TestElementNodeFactory {

    JsonAssertionFactory(String testElementName) {
        super(testElementName, JSONPathAssertion, JSONPathAssertionGui, true, DslDefinition.ASSERT_JSON_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String jpath = readValue(config.jpath, '$.')
        boolean assertValue = readValue(config.assert_value, false)
        boolean assertAsRegex = readValue(config.assert_as_regex, true)
        String regexValue = readValue(config.value, '')
        boolean expectNull = readValue(config.expectNull, false)
        boolean invert = readValue(config.invert, false)

        testElement.setJsonPath(jpath)
        testElement.setExpectedValue(regexValue)
        testElement.setJsonValidationBool(assertValue)
        testElement.setExpectNull(expectNull)
        testElement.setInvert(invert)
        testElement.setIsRegex(assertAsRegex)
    }
}
