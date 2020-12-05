/*
 * Copyright 2019 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.config

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.control.Header
import org.apache.jmeter.protocol.http.control.HeaderManager
import org.apache.jmeter.protocol.http.gui.HeaderPanel
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>headers</code> element in the test.
 * <p>
 * It can define user variables in two ways. The short one with version property, and normal with nested {@link HeaderFactory}.
 *
 * <pre>
 * // structure with nested argument
 * headers {
 *     {@link HeaderFactory header}
 * }
 *
 * // structure without nested argument
 * headers (
 *    values: map
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             headers values: [
 *                 header1: 'value1',
 *                 header2: 'value2',
 *                 'X-HEADER-NAME': 'value3'
 *             ]
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Header_Manager">HTTP Header Manager</a>
 *
 * @see HeaderFactory HeaderFactory
 */
@CompileDynamic
final class HeadersFactory extends TestElementNodeFactory {

    HeadersFactory(String testElementName) {
        super(testElementName, HeaderManager, HeaderPanel, false, DslDefinition.HEADERS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        Object values = readValue(config.values, [:])

        values.each { k, v ->
            Header header = new Header()

            header.name = readValue(k, '')
            header.value = readValue(v, '')

            testElement.add(header)
        }
    }
}
