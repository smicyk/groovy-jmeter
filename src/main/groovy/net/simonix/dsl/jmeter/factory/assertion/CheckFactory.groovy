/*
 * Copyright 2022 Szymon Micyk
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
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import net.simonix.dsl.jmeter.validation.ValidatorProvider
import net.simonix.dsl.jmeter.handler.CheckRequestHandler
import net.simonix.dsl.jmeter.handler.CheckSizeHandler
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.handler.CheckResponseHandler
import net.simonix.dsl.jmeter.validation.PropertyValidator

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The base factory class responsible for building <code>check_response, check_request, check_size</code> elements in the test.
 *
 * <pre>
 * // general check structure
 * check_* (
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
 *
 *                 check_request {
 *                     headers(not) contains 'COOKIES', 'no cookies'
 *                     headers() contains 'COOKIES', 'has cookies'
 *
 *                     text(not) contains 'test text'
 *                     text() contains 'test text'
 *                 }
 *
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
 * @see CheckRequestFactory
 * @see CheckResponseFactory
 * @see CheckSizeFactory
 */
@CompileDynamic
abstract class CheckFactory extends AbstractFactory implements ValidatorProvider {

    final PropertyValidator validator
    final KeywordDefinition definition

    protected CheckFactory(KeywordDefinition definition) {
        this.definition = definition

        this.validator = new PropertyValidator(definition.properties, definition.valueIsProperty)
    }

    abstract CheckHandler createCheckHandler(CheckTestElementNode node, FactoryBuilderSupport builder)

    abstract void updateOnComplete(Object parent, Object child)

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        String applyTo = readValue(value, config.applyTo)

        return new CheckTestElementNode(applyTo)
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config) {
        return false
    }

    boolean isHandlesNodeChildren() {
        return true
    }

    boolean isLeaf() {
        return definition.leaf
    }

    boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure c) {
        CheckHandler handler = createCheckHandler((CheckTestElementNode) node, builder)

        def resolver = c.rehydrate(handler, this, this)
        resolver.resolveStrategy = Closure.DELEGATE_ONLY
        resolver()

        return false
    }

    void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node ) {
        if (parent instanceof TestElementNode && node instanceof CheckTestElementNode) {
            node.testElementNodes.each {
                parent.add(it)
            }
        }
    }
}
