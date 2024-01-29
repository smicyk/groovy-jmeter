/*
 * Copyright 2024 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.controller

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.LoopController
import org.apache.jmeter.control.gui.LoopControlPanel
import org.apache.jmeter.testelement.TestElement

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.hasValue
import static net.simonix.dsl.jmeter.utils.LoopControllerUtils.updateLoopConfig

/**
 * The factory class responsible for building <code>loop</code> element in the test.
 *
 * <pre>
 * // element structure
 * loop (
 *     count: integer [<strong>1</strong>]
 *     forever: boolean [<strong>false</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Loop_Controller">Loop Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class LoopFactory extends TestElementNodeFactory {

    LoopFactory() {
        super(LoopController, LoopControlPanel, DslDefinition.LOOP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        Object count = config.count

        if (value) {
            count = value
        }

        updateLoopConfig(testElement, count, hasValue(config.forever), config.forever)
    }
}
