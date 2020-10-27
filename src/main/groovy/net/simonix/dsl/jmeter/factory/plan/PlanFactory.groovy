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
package net.simonix.dsl.jmeter.factory.plan

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.gui.ArgumentsPanel
import org.apache.jmeter.control.gui.TestPlanGui
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.TestPlan

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>plan</code> element in the test.
 * <p>
 * The <code>plan</code> is the root element for each test.
 *
 * <pre>
 * // structure of the element
 * plan (
 *     serialized: boolean         [<strong>false</strong>]
 *     functionalMode: boolean     [<strong>false</strong>]
 *     tearDownOnShutdown: boolean [<strong>false</strong>]
 * ) {
 *     {@link net.simonix.dsl.jmeter.factory.common.ArgumentsFactory arguments}
 *     {@link net.simonix.dsl.jmeter.factory.group.GroupFactory group}
 * }
 *
 * // example usage
 * start {
 *     plan(serialized: true) {
 *         arguments {
 *             // list of arguments
 *         }
 *         group {
 *             // other configurations
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Test_Plan">JMeter Test Plan</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class PlanFactory extends TestElementNodeFactory {

    PlanFactory(String testElementName) {
        super(testElementName, TestPlan, TestPlanGui, false, DslDefinition.PLAN_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.serialized = readValue(config.serialized, false)
        testElement.functionalMode = readValue(config.functionalMode, false)
        testElement.tearDownOnShutdown = readValue(config.tearDownOnShutdown, false)
        testElement.testPlanClasspath = ''

        Arguments arguments = new Arguments()
        arguments.enabled = true
        arguments.setProperty(TestElement.TEST_CLASS, Arguments.name)
        arguments.setProperty(TestElement.GUI_CLASS, ArgumentsPanel.name)

        testElement.userDefinedVariables = arguments
    }
}
