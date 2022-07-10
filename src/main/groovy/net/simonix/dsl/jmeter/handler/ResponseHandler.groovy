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
import net.simonix.dsl.jmeter.model.TestElementNode

@CompileDynamic
final class ResponseHandler {

    TestElementNode testElementCurrent

    ResponseHandler(TestElementNode testElementCurrent) {
        this.testElementCurrent = testElementCurrent
    }

    ResponseHandler ne(int value, String message = '') {
        testElementCurrent.testElement.addTestString(value.toString())
        testElementCurrent.testElement.setToEqualsType()
        testElementCurrent.testElement.setToNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler ne(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToEqualsType()
        testElementCurrent.testElement.setToNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler eq(int value, String message = '') {
        testElementCurrent.testElement.addTestString(value.toString())
        testElementCurrent.testElement.setToEqualsType()
        testElementCurrent.testElement.unsetNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler eq(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToEqualsType()
        testElementCurrent.testElement.unsetNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler substring(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToSubstringType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler includes(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToContainsType()
        testElementCurrent.testElement.unsetNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler excludes(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToContainsType()
        testElementCurrent.testElement.setToNotType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler matches(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToMatchType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler and(int value, String message = '') {
        testElementCurrent.testElement.addTestString(value.toString())
        testElementCurrent.testElement.unsetOrType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler and(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.unsetOrType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler or(int value, String message = '') {
        testElementCurrent.testElement.addTestString(value.toString())
        testElementCurrent.testElement.setToOrType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }

    ResponseHandler or(String value, String message = '') {
        testElementCurrent.testElement.addTestString(value)
        testElementCurrent.testElement.setToOrType()

        testElementCurrent.testElement.setCustomFailureMessage(message)

        return this
    }
}
