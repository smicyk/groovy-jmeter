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

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class CheckRequestHandler implements CheckHandler {

    CheckTestElementNode testElementContainer
    TestElementNode testElementCurrent

    FactoryBuilderSupport builderSupport

    CheckRequestHandler(CheckTestElementNode testElementContainer, FactoryBuilderSupport builderSupport) {
        this.testElementContainer = testElementContainer
        this.builderSupport = builderSupport
    }

    ResponseHandler headers(String value) {
        return headers([:], value)
    }

    ResponseHandler headers(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Headers'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'request_headers')
    }

    ResponseHandler text(String value) {
        return text([:], value)
    }

    ResponseHandler text(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Text'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'request_data')
    }

    private ResponseHandler responseBuildImpl(String type, String name, String comment, boolean enabled, String applyTo, String field) {
        Factory factory = builderSupport.factories.get(type)

        Map config = [:]
        config.name = name
        config.comment = comment
        config.enabled = enabled
        config.applyTo = applyTo
        config.field = field

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new ResponseHandler(testElementCurrent)
    }
}
