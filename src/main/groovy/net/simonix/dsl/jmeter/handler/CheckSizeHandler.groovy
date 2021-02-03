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
final class CheckSizeHandler implements CheckHandler {

    final static boolean not = true

    CheckTestElementNode testElementContainer
    TestElementNode testElementCurrent

    FactoryBuilderSupport builderSupport

    CheckSizeHandler(CheckTestElementNode testElementContainer, FactoryBuilderSupport builderSupport) {
        this.testElementContainer = testElementContainer
        this.builderSupport = builderSupport
    }

    SizeHandler status() {
        String applyTo = testElementContainer.applyTo

        return sizeBuildImpl('assert_size', 'Check Size', applyTo, 'response_status')
    }

    SizeHandler headers() {
        String applyTo = testElementContainer.applyTo

        return sizeBuildImpl('assert_size', 'Check Size', applyTo, 'response_headers')
    }

    SizeHandler text() {
        String applyTo = testElementContainer.applyTo

        return sizeBuildImpl('assert_size', 'Check Size', applyTo, 'response_data')
    }

    SizeHandler body() {
        String applyTo = testElementContainer.applyTo

        return sizeBuildImpl('assert_size', 'Check Size', applyTo, 'response_body')
    }

    SizeHandler message() {
        String applyTo = testElementContainer.applyTo

        return sizeBuildImpl('assert_size', 'Check Size', applyTo, 'response_message')
    }

    private SizeHandler sizeBuildImpl(String type, String name, String applyTo, String field) {
        Factory factory = builderSupport.getFactories().get(type)

        Map config = [:]
        config.name = name
        config.applyTo = applyTo
        config.field = field
        config.rule = 'ne'

        testElementCurrent = factory.newInstance(builderSupport, type, null, config) as TestElementNode
        testElementContainer.add(testElementCurrent)

        return new SizeHandler(testElementCurrent)
    }
}
