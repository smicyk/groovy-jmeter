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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.common.ArgumentFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * Builds the single <code>argument</code> for test element. It is used with conjunction with <code>arguments</code>.
 *
 * <pre>
 * // structure of the argument
 * argument (
 *     name: string
 *     value: string
 * )
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
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.listener.BackendArgumentsFactory BackendArgumentsFactory
 * @see net.simonix.dsl.jmeter.factory.listener.BackendListenerFactory BackendListenerFactory
 */
@CompileDynamic
final class BackendArgumentFactory extends ArgumentFactory {

    BackendArgumentFactory() {
        super(DslDefinition.BACKEND_ARGUMENT)
    }
}
