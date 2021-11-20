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
import net.simonix.dsl.jmeter.handler.CheckRequestHandler
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * The factory class responsible for building <code>check_request</code> elements in the test.
 *
 * <pre>
 * // general check structure
 * check_request (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 * ) {
 *    {@link CheckRequestHandler request}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             http 'GET http://www.example.com', {
 *                 check_request {
 *                     headers(not) contains 'COOKIES', 'no cookies'
 *                     headers() contains 'COOKIES', 'has cookies'
 *
 *                     text(not) contains 'test text'
 *                     text() contains 'test text'
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
final class CheckRequestFactory extends CheckFactory {

    CheckRequestFactory() {
        super(DslDefinition.CHECK_REQUEST)
    }

    @Override
    CheckHandler createCheckHandler(CheckTestElementNode node, FactoryBuilderSupport builder) {
        return new CheckRequestHandler(node, builder)
    }

    @Override
    void updateOnComplete(Object parent, Object child) {
        // empty implementation
    }
}
