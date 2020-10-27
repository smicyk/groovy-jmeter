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
package net.simonix.dsl.jmeter.factory.controller.execution

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.control.ThroughputController
import org.apache.jmeter.control.gui.ThroughputControllerGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>execute_total</code> element in the test.
 *
 * <pre>
 * // element structure
 * execute_total (
 *     total: integer [<strong>1</strong>]
 *     perUser: boolean [<strong>false</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Throughput_Controller">Throughput Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see net.simonix.dsl.jmeter.factory.controller.ExecuteFactory ExecuteFactory
 */
@CompileDynamic
final class TotalControllerFactory extends TestElementNodeFactory {

    TotalControllerFactory() {
        super('Total Execution Controller', ThroughputController, ThroughputControllerGui, false, DslDefinition.EXECUTE_TOTAL_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean perUser = readValue(config.perUser, false)
        Integer total = (Integer) readValue(value, readValue(config.total, 1))

        testElement.style = ThroughputController.BYNUMBER
        testElement.perThread = perUser
        testElement.maxThroughput = total
        testElement.percentThroughput = 100
    }
}
