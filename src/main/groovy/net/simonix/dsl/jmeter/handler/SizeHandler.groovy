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
import org.apache.jmeter.assertions.SizeAssertion

@CompileDynamic
final class SizeHandler {

    TestElementNode testElementCurrent

    SizeHandler(TestElementNode testElementCurrent) {
        this.testElementCurrent = testElementCurrent
    }

    SizeHandler eq(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.EQUAL)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }

    SizeHandler ne(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.NOTEQUAL)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }

    SizeHandler lt(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.LESSTHAN)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }

    SizeHandler gt(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.GREATERTHAN)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }

    SizeHandler le(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.LESSTHANEQUAL)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }

    SizeHandler ge(long value) {
        testElementCurrent.testElement.setCompOper(SizeAssertion.GREATERTHANEQUAL)
        testElementCurrent.testElement.setAllowedSize(value)

        return this
    }
}
