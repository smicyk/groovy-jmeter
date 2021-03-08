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
package net.simonix.dsl.jmeter.factory.controller.execution

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.RunTime
import org.apache.jmeter.control.gui.RunTimeGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>execute_runtime</code> element in the test.
 *
 * <pre>
 * // element structure
 * execute_runtime (
 *     runtime: integer [<strong>1</strong>
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Runtime_Controller">Runtime Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see net.simonix.dsl.jmeter.factory.controller.ExecuteFactory ExecuteFactory
 */
@CompileDynamic
final class RuntimeControllerFactory extends TestElementNodeFactory {

    RuntimeControllerFactory() {
        super(DslDefinition.EXECUTE_RUNTIME.title, RunTime, RunTimeGui, DslDefinition.EXECUTE_RUNTIME.leaf, DslDefinition.EXECUTE_RUNTIME)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.runtime = readValue(value, config.runtime)
    }
}
