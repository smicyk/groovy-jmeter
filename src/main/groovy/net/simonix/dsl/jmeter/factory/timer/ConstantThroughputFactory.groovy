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
package net.simonix.dsl.jmeter.factory.timer

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.DoubleProperty
import org.apache.jmeter.timers.ConstantThroughputTimer

/**
 * The factory class responsible for building <code>constant_throughput</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * constant_throughput (
 *     target: double   [<strong>0.0</strong>]
 *     basedOn: string  [<strong>user</strong>, all_users, all_users_shared, all_users_in_group, all_users_in_group_shared]
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             constant_throughput target: 10.0
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Constant_Throughput_Timer">Constant Timer</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class ConstantThroughputFactory extends TestElementNodeFactory {

    ConstantThroughputFactory(String testElementName) {
        super(testElementName, ConstantThroughputTimer, TestBeanGUI, true, DslDefinition.CONSTANT_THROUGHPUT)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        int basedOn = mapBasedOn(config.basedOn as String)

        testElement.calcMode = basedOn
        testElement.throughput = config.target

        testElement.setProperty('calcMode', basedOn)
        testElement.setProperty(new DoubleProperty('throughput', config.target as Double))
    }

    private int mapBasedOn(String value) {
        if(value == "user") {
            return ConstantThroughputTimer.Mode.ThisThreadOnly.ordinal();
        } else if(value == "all_users") {
            return ConstantThroughputTimer.Mode.AllActiveThreads.ordinal();
        } else if(value == "all_users_shared") {
            return ConstantThroughputTimer.Mode.AllActiveThreads_Shared.ordinal();
        } else if(value == "all_users_in_group") {
            return ConstantThroughputTimer.Mode.AllActiveThreadsInCurrentThreadGroup.ordinal();
        } else if(value == "all_users_in_group_shared") {
            return ConstantThroughputTimer.Mode.AllActiveThreadsInCurrentThreadGroup_Shared.ordinal();
        } else {
            return ConstantThroughputTimer.Mode.ThisThreadOnly.ordinal();
        }
    }
}
