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
import net.simonix.dsl.jmeter.handler.CheckHandler
import net.simonix.dsl.jmeter.handler.CheckResponseHandler
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * The factory class responsible for building <code>check_response</code> elements in the test.
 *
 * <pre>
 * // general check structure
 * check_response (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 * ) {
 *    {@link CheckResponseHandler response}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             http 'GET http://www.example.com', {
 *                 check_response {
 *                     // check status code
 *                     status(not) eq 200 and 201 and 202, 'is not 2xx'
 *                     status() eq 500
 *
 *                     // check status with substring
 *                     status() substring '500' and '400'
 *
 *                     // check status with pattern
 *                     status() contains 'pattern \\d+' or 'test 123'
 *
 *                     // check headers with pattern
 *                     headers(not) contains 'COOKIES', 'no cookies'
 *                     headers() contains 'COOKIES', 'has cookies'
 *
 *                     // check body with pattern
 *                     text(not) contains 'text'
 *                     text() contains 'text'
 *
 *                     // check document with pattern
 *                     document(not) contains 'text'
 *                     document() contains 'text'
 *
 *                     // check message
 *                     message(not) eq 'OK', 'Not OK'
 *                     message() eq 'OK', 'OK'
 *
 *                     // check url with pattern
 *                     url(not) contains 'localhost', 'external'
 *                     url() contains 'localhost', 'local'
 *
 *                     // check duration
 *                     duration() eq 2000
 *
 *                     // check md5hex
 *                     md5hex() eq '342sdf234sdf234fs3rf34f223f'
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Response_Assertion">Response Assertion</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class CheckResponseFactory extends CheckFactory {

    CheckResponseFactory() {
        super(DslDefinition.CHECK_RESPONSE)
    }

    @Override
    CheckHandler createCheckHandler(CheckTestElementNode node, FactoryBuilderSupport builder) {
        return new CheckResponseHandler((CheckTestElementNode) node, builder)
    }
}
