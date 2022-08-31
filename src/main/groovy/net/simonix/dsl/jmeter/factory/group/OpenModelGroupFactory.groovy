/*
 * Copyright 2022 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.group

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty
import org.apache.jmeter.threads.AbstractThreadGroup
import org.apache.jmeter.threads.openmodel.OpenModelThreadGroup
import org.apache.jmeter.threads.openmodel.OpenModelThreadGroupController
import org.apache.jmeter.threads.openmodel.gui.OpenModelThreadGroupGui

import static net.simonix.dsl.jmeter.utils.ConfigUtils.loadFromFile
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>schedule</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * schedule (
 *     inline: string
 *     file: string
 *     seed: integer
 *     onError: string       [<strong>continue</strong>, start_next, stop_user, stop_test, stop_now]
 * ) {
 *   groups | controllers | samplers | config
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         schedule inline: '''
 *             rate(1/min) random_arrivals(10 min) pause(1 min)
 *         ''', {
 *             // other configurations
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Open_Model_Thread_Group">Open Model Thread Group</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
class OpenModelGroupFactory extends TestElementNodeFactory {

    OpenModelGroupFactory() {
        super(OpenModelThreadGroup, OpenModelThreadGroupGui, DslDefinition.OPEN_MODEL_GROUP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // set default controller as loop (that seems to be jmeter defaults)
        testElement.setProperty(new TestElementProperty(AbstractThreadGroup.MAIN_CONTROLLER, new OpenModelThreadGroupController()))

        testElement.setProperty(AbstractThreadGroup.ON_SAMPLE_ERROR, mapOnError(config.onError))

        String schedule = readValue(value, config.inline)

        if (!schedule) {
            schedule = loadFromFile(config.file as String, 'UTF-8')
        }

        testElement.scheduleString = schedule
        testElement.randomSeedString = config.seed as String
    }

    String mapOnError(String value) {
        if (value == 'continue') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_CONTINUE
        } else if (value == 'start_next') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_START_NEXT_LOOP
        } else if (value == 'stop_user') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTHREAD
        } else if (value == 'stop_test') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTEST
        } else if (value == 'stop_now') {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_STOPTEST_NOW
        } else {
            return AbstractThreadGroup.ON_SAMPLE_ERROR_CONTINUE
        }
    }
}
