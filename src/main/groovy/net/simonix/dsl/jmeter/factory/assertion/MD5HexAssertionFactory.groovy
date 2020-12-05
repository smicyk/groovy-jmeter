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
import org.apache.jmeter.assertions.MD5HexAssertion
import org.apache.jmeter.assertions.gui.MD5HexAssertionGUI
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>assert_md5hex</code> element in the test.
 *
 * <pre>
 * // element structure
 * assert_md5hex (
 *    value: string
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#MD5Hex_Assertion">MD5Hex Assertion</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class MD5HexAssertionFactory extends TestElementNodeFactory {

    MD5HexAssertionFactory(String testElementName) {
        super(testElementName, MD5HexAssertion, MD5HexAssertionGUI, true, DslDefinition.ASSERT_MD5HEX)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String md5hex = readValue(config.value, '')

        testElement.setAllowedMD5Hex(md5hex)
    }
}
