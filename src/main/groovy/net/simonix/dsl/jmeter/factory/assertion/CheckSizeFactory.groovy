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
import net.simonix.dsl.jmeter.handler.CheckResponseHandler
import net.simonix.dsl.jmeter.handler.CheckSizeHandler
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * The factory class responsible for building <code>check_size</code> elements in the test.
 *
 * <pre>
 * // general check structure
 * check_size (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 * ) {
 *    {@link CheckResponseHandler response} || {@link CheckRequestHandler request} || {@link CheckSizeHandler size}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             http 'GET http://www.example.com', {
 *                 check_size {
 *                     status() eq 200
 *                     status() ne 200
 *                     status() gt 200
 *                     status() lt 200
 *                     status() ge 200
 *                     status() le 200
 *
 *                     headers() eq 200
 *                     text() eq 200
 *                     body() eq 200
 *                     message() eq 200
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
final class CheckSizeFactory extends CheckFactory {

    CheckSizeFactory() {
        super(DslDefinition.CHECK_SIZE)
    }

    @Override
    CheckHandler createCheckHandler(CheckTestElementNode node, FactoryBuilderSupport builder) {
        return new CheckSizeHandler(node, builder)
    }
}
