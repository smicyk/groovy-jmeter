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
import net.simonix.dsl.jmeter.model.CheckTestElementNode
import net.simonix.dsl.jmeter.model.TestElementNode

@CompileDynamic
final class CheckRequestHandler implements CheckHandler {

    final static boolean not = true

    CheckTestElementNode testElementContainer
    TestElementNode testElementCurrent

    FactoryBuilderSupport builderSupport

    CheckRequestHandler(CheckTestElementNode testElementContainer, FactoryBuilderSupport builderSupport) {
        this.testElementContainer = testElementContainer
        this.builderSupport = builderSupport
    }

    ResponseHandler headers(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Headers', applyTo, 'request_headers', negate)
    }

    ResponseHandler text(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Text', applyTo, 'request_data', negate)
    }

    private ResponseHandler responseBuildImpl(String type, String name, String applyTo, String field, boolean negate) {
        Factory factory = builderSupport.factories.get(type)

        Map config = [:]
        config.name = name
        config.applyTo = applyTo
        config.field = field
        config.negate = negate

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new ResponseHandler(testElementCurrent)
    }
}
