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
final class CheckResponseHandler implements CheckHandler {

    CheckTestElementNode testElementContainer
    TestElementNode testElementCurrent

    FactoryBuilderSupport builderSupport

    CheckResponseHandler(CheckTestElementNode testElementContainer, FactoryBuilderSupport builderSupport) {
        this.testElementContainer = testElementContainer
        this.builderSupport = builderSupport
    }

    ResponseHandler status(String value) {
        return status([:], value)
    }

    ResponseHandler status(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Status'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_code')
    }

    ResponseHandler headers(String value) {
        return headers([:], value)
    }

    ResponseHandler headers(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Headers'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_headers')
    }

    ResponseHandler text(String value) {
        return text([:], value)
    }

    ResponseHandler text(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Text'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_text')
    }

    ResponseHandler document(String value) {
        return document([:], value)
    }

    ResponseHandler document(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Document'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_document')
    }

    ResponseHandler message(String value) {
        return message([:], value)
    }

    ResponseHandler message(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Message'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_message')
    }

    ResponseHandler url(String value) {
        return url([:], value)
    }

    ResponseHandler url(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Url'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return responseBuildImpl('assert_response', name, comment, enabled, applyTo, 'response_url')
    }

    DurationHandler duration(String value) {
        return duration([:], value)
    }

    DurationHandler duration(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check Duration'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return durationBuildImpl('assert_duration', name, comment, enabled, applyTo)
    }

    MD5HexHandler md5hex(String value) {
        return md5hex([:], value)
    }

    MD5HexHandler md5hex(Map config = [:], String value = '') {
        String applyTo = testElementContainer.applyTo
        String name = readValue(config.name, readValue(value, 'Check MD5Hex'))
        String comment = readValue(config.comments, '')
        boolean enabled = readValue(config.enabled, testElementContainer.enabled)

        return md5hexBuildImpl('assert_md5hex', name, comment, enabled, applyTo)
    }

    private ResponseHandler responseBuildImpl(String type, String name, String comment, boolean enabled, String applyTo, String field) {
        Factory factory = builderSupport.getFactories().get(type)

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

    private DurationHandler durationBuildImpl(String type, String name, String comment, boolean enabled, String applyTo) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.comment = comment
        config.enabled = enabled
        config.applyTo = applyTo

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new DurationHandler(testElementCurrent)
    }

    private MD5HexHandler md5hexBuildImpl(String type, String name, String comment, boolean enabled, String applyTo) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.comment = comment
        config.enabled = enabled
        config.applyTo = applyTo

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new MD5HexHandler(testElementCurrent)
    }
}
