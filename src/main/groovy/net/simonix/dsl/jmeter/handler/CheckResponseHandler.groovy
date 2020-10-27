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
final class CheckResponseHandler {

    final static boolean not = true

    CheckTestElementNode testElementContainer
    TestElementNode testElementCurrent

    FactoryBuilderSupport builderSupport

    CheckResponseHandler(CheckTestElementNode testElementContainer, FactoryBuilderSupport builderSupport) {
        this.testElementContainer = testElementContainer
        this.builderSupport = builderSupport
    }

    ResponseHandler status(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Status', applyTo, 'response_code', negate)
    }

    ResponseHandler headers(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Headers', applyTo, 'response_headers', negate)
    }

    ResponseHandler text(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Text', applyTo, 'response_text', negate)
    }

    ResponseHandler document(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Document', applyTo, 'response_document', negate)
    }

    ResponseHandler message(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Message', applyTo, 'response_message', negate)
    }

    ResponseHandler url(boolean negate = false) {
        String applyTo = testElementContainer.applyTo

        return responseBuildImpl('assert_response', 'Check Url', applyTo, 'response_url', negate)
    }

    DurationHandler duration() {
        String applyTo = testElementContainer.applyTo

        return durationBuildImpl('assert_duration', 'Check duration', applyTo)
    }

    MD5HexHandler md5hex() {
        String applyTo = testElementContainer.applyTo

        return md5hexBuildImpl('assert_md5hex', 'Check MD5Hex', applyTo)
    }

    private ResponseHandler responseBuildImpl(String type, String name, String applyTo, String field, boolean negate) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.applyTo = applyTo
        config.field = field
        config.negate = negate

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new ResponseHandler(testElementCurrent)
    }

    private DurationHandler durationBuildImpl(String type, String name, String applyTo) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.applyTo = applyTo

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new DurationHandler(testElementCurrent)
    }

    private MD5HexHandler md5hexBuildImpl(String type, String name, String applyTo) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.applyTo = applyTo

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new MD5HexHandler(testElementCurrent)
    }
}
