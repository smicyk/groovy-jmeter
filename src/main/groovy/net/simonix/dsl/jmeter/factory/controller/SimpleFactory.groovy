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
package net.simonix.dsl.jmeter.factory.controller

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.GenericController
import org.apache.jmeter.control.gui.LogicControllerGui

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

/**
 * The factory class responsible for building <code>simple</code> element in the test.
 *
 * <pre>
 * // element structure
 * simple {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Simple_Controller">Simple Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class SimpleFactory extends TestElementNodeFactory {

    SimpleFactory() {
        super(DslDefinition.SIMPLE.title, GenericController, LogicControllerGui, false, DslDefinition.SIMPLE)
    }
}
