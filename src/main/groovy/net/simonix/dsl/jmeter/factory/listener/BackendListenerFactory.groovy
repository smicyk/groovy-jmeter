/*
 * Copyright 2021 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.listener

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty
import org.apache.jmeter.visualizers.backend.BackendListener
import org.apache.jmeter.visualizers.backend.BackendListenerGui

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class BackendListenerFactory extends TestElementNodeFactory {

    BackendListenerFactory() {
        super(BackendListener, BackendListenerGui, DslDefinition.BACKEND)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.classname = config.classname
        testElement.queueSize = config.queueSize?.toString()

        testElement.property = new TestElementProperty(BackendListener.ARGUMENTS, new Arguments())
    }
}
