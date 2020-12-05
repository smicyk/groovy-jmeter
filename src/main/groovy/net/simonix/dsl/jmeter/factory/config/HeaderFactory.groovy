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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.control.Header
import org.apache.jmeter.protocol.http.control.HeaderManager
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>header</code> element in the test.
 * <p>
 * It is used as child element of <pre>headers</pre> test element.
 *
 * <pre>
 * // structure with nested argument
 * header (
 *    name: string
 *    value: string
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             headers {
 *                 header(name: 'header1', value: 'value1')
 *                 header(name: 'header2', value: 'value2')
 *             }
 *         }
 *     }
 * }
 * @see HeadersFactory HeadersFactory
 */
@CompileDynamic
final class HeaderFactory extends TestElementFactory {

    HeaderFactory() {
        super(Header, DslDefinition.HEADER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.name = readValue(config.name, '')
        testElement.value = readValue(config.value, '')
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object manager, Object header) {
        if (manager instanceof HeaderManager && header instanceof Header) {
            manager.add(header)
        }
    }
}
