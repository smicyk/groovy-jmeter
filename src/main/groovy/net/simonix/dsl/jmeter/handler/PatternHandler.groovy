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
package net.simonix.dsl.jmeter.handler

import groovy.transform.CompileDynamic
import org.apache.jmeter.assertions.ResponseAssertion
import org.apache.jmeter.testelement.TestElement

/**
 * The handler class responsible for interpret <code>pattern</code> element in the test.
 * <p>
 * The <code>pattern</code> element is used in assertions.
 *
 * <pre>
 * // element structure
 * pattern string
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.assertion.ResponseAssertionFactory RespnoseAssertionFactory
 */
@CompileDynamic
final class PatternHandler {

    TestElement testElement

    PatternHandler(TestElement testElement) {
        this.testElement = testElement
    }

    void pattern(String value) {
        if(testElement instanceof ResponseAssertion) {
            testElement.addTestString(value)
        }
    }
}
