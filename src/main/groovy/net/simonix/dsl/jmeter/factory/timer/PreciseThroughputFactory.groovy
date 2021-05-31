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
import org.apache.jmeter.timers.poissonarrivals.PreciseThroughputTimer

/**
 * The factory class responsible for building <code>precise_throughput</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * precise_throughput (
 *     target: double        [<strong>100.0</strong>]
 *     period: integer       [<strong>3600</strong>]
 *     duration: long        [<strong>3600</strong>]
 *     batchUsers: integer   [<strong>1</strong>]
 *     batchDelay: integer   [<strong>0</strong>]
 *     samples: integer      [<strong>10000</strong>]
 *     percents: double      [<strong>1.0</strong>]
 *     seed: long            [<strong>0</strong>]
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             precise_throughput target: 500, period: 1000, duration: 1000
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Precise_Throughput_Timer">Precise Throughput Timer</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class PreciseThroughputFactory extends TestElementNodeFactory {

    PreciseThroughputFactory() {
        super(DslDefinition.PRECISE_THROUGHPUT.title, PreciseThroughputTimer, TestBeanGUI, DslDefinition.PRECISE_THROUGHPUT.leaf, DslDefinition.PRECISE_THROUGHPUT)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.throughput = config.target
        testElement.throughputPeriod = config.period
        testElement.duration = config.duration

        testElement.batchSize = config.batchUsers
        testElement.batchThreadDelay = config.batchDelay

        testElement.exactLimit = config.samples
        testElement.allowedThroughputSurplus = config.percents

        testElement.randomSeed = config.seed

        testElement.setProperty(new DoubleProperty('throughput', config.target as Double))
        testElement.setProperty('throughputPeriod', config.period as Integer)
        testElement.setProperty('duration', config.duration as Long)

        testElement.setProperty('batchSize', config.batchUsers as Integer)
        testElement.setProperty('batchThreadDelay', config.batchDelay as Integer)

        testElement.setProperty('exactLimit', config.samples as Integer)
        testElement.setProperty(new DoubleProperty('allowedThroughputSurplus', config.percents as Double))

        testElement.setProperty('randomSeed', config.seed as Long)
    }
}
