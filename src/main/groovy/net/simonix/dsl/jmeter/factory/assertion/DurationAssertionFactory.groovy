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
package net.simonix.dsl.jmeter.factory.assertion

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.assertions.DurationAssertion
import org.apache.jmeter.assertions.gui.DurationAssertionGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_duration</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_response (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 *    duration: long
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Duration_Assertion">Duration Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DurationAssertionFactory extends TestElementNodeFactory {

    DurationAssertionFactory() {
        super(DslDefinition.ASSERT_DURATION.title, DurationAssertion, DurationAssertionGui, DslDefinition.ASSERT_DURATION.leaf, DslDefinition.ASSERT_DURATION)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = config.applyTo
        Long duration = readValue(value, config.duration)

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

        testElement.allowedDuration = duration
    }
}
