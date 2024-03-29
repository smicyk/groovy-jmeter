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
package net.simonix.dsl.jmeter.factory.assertion

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.assertions.jmespath.JMESPathAssertion
import org.apache.jmeter.assertions.jmespath.gui.JMESPathAssertionGui
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>assert_jmes</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_jmes (
 *    expression: string [<strong>$.<strong>]
 *    assertValue: boolean [false]
 *    assertAsRegex: boolean [true]
 *    value: string
 *    expectNull: boolean [false]
 *    invert: boolean [false]
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSON_JMESPath_Assertion">JSON Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class JMESAssertionFactory extends TestElementNodeFactory {

    JMESAssertionFactory() {
        super(JMESPathAssertion, JMESPathAssertionGui, DslDefinition.ASSERT_JMES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String jpath = config.expression
        boolean assertValue = config.assertValue
        boolean assertAsRegex = config.assertAsRegex
        String regexValue = config.value
        boolean expectNull = config.expectNull
        boolean invert = config.invert

        testElement.jmesPath = jpath
        testElement.expectedValue = regexValue
        testElement.jsonValidationBool = assertValue
        testElement.expectNull = expectNull
        testElement.invert = invert
        testElement.isRegex = assertAsRegex
    }
}
