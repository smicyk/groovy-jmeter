/*
 * Copyright 2019 Szymon Micyk
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
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jmeter.reporters.Summariser
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.visualizers.StatVisualizer

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class AggregateFactory extends TestElementNodeFactory {

    AggregateFactory(String testElementName) {
        super(testElementName, ResultCollector, StatVisualizer, true, DslDefinition.AGGREGATE_PROPERTIES)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Summariser summariser = new Summariser()

        return new ResultCollector(summariser)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.filename = readValue(config.file, '')
    }
}
