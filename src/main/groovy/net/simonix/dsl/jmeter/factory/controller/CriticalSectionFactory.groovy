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
package net.simonix.dsl.jmeter.factory.controller

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.control.CriticalSectionController
import org.apache.jmeter.control.gui.CriticalSectionControllerGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>section</code> element in the test.
 *
 * <pre>
 * // element structure
 * section (
 *     lock: string [<strong>global_lock</strong>]
 * ) {
 *     controllers | samplers | config
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Critical_Section_Controller">Critical Section Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class CriticalSectionFactory extends TestElementNodeFactory {

    CriticalSectionFactory() {
        super(DslDefinition.SECTION.title, CriticalSectionController, CriticalSectionControllerGui, DslDefinition.SECTION.leaf, DslDefinition.SECTION)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.lockName = config.lock
    }
}
