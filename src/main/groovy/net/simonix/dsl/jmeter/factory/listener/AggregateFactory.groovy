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
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jmeter.reporters.Summariser
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.visualizers.StatVisualizer

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>aggregate</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * aggregate (
 *   file: string
 * ) {
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         aggregate 'filename.jtl'
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Aggregate_Report">Aggregate Report</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class AggregateFactory extends TestElementNodeFactory {

    AggregateFactory() {
        super(ResultCollector, StatVisualizer, DslDefinition.AGGREGATE)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Summariser summariser = new Summariser()

        return new ResultCollector(summariser)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.filename = readValue(value, config.file)
    }
}
