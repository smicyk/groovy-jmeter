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

/**
 * The factory class responsible for building <code>backend</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * backend (
 *   classname: string  [<strong>org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient</strong>]
 *   queueSize: integer [<strong>5000</strong>]
 * ) {
 *     {@link BackendArgumentsFactory arguments}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         backend {
 *             arguments {
 *                 argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
 *                 argument(name: 'influxdbUrl', value: 'http://influxdb-db:8080/write?db=jmeter')
 *                 argument(name: 'application', value: 'application')
 *                 argument(name: 'measurement', value: 'performance')
 *                 argument(name: 'summaryOnly', value: 'false')
 *                 argument(name: 'samplersRegex', value: '.*')
 *                 argument(name: 'percentiles', value: '90;95;99')
 *                 argument(name: 'testTitle', value: 'application - users: 100, rampup: 60')
 *                 argument(name: "eventTags", value: '')
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Backend_Listener">Backend Listener</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
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
