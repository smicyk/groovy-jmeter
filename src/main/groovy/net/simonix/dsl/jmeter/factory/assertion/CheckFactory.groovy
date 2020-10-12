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

import net.simonix.dsl.jmeter.handler.CheckRequestHandler
import net.simonix.dsl.jmeter.handler.CheckSizeHandler
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.handler.CheckResponseHandler

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>check</code> element in the test.
 *
 * <pre>
 * // element structure
 * check (
 *    applyTo: string [<strong>all</strong>, parent, children, variable]
 *    type: string [<strong>response</strong>, request]
 * ) {
 *    {@link CheckResponseHandler handler}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Response_Assertion">Response Assertion</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
final class CheckFactory extends AbstractFactory {
    final String type

    CheckFactory(String type) {
        this.type = type
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        String applyTo = readValue(value, readValue(config.applyTo, 'all'))

        return new CheckTestElementNode(applyTo)
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config) {
        return false
    }

    boolean isHandlesNodeChildren() {
        return true
    }

    boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure c) {
        def handler
        if(type == 'response') {
            handler = new CheckResponseHandler(node, builder)
        } else if(type == 'request') {
            handler = new CheckRequestHandler(node, builder)
        } else if(type == 'size') {
            handler = new CheckSizeHandler(node, builder)
        }

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