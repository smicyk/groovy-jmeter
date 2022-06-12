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
package net.simonix.dsl.jmeter.factory.extractor

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.extractor.json.jmespath.JMESPathExtractor
import org.apache.jmeter.extractor.json.jmespath.gui.JMESPathExtractorGui
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>extract_jmes</code> element in the test.
 *
 * <pre>
 * // element structure
 * extract_jmes (
 *     applyTo: string [<strong>parent</strong>, all, children, variable]
 *     variable: string
 *     expression: string
 *     match: integer [<strong>1</strong>]
 *     defaultValue: string
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JSON_JMESPath_Extractor">JSON JMES Extractor</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class JMESPathExtractorFactory extends TestElementNodeFactory {

    JMESPathExtractorFactory() {
        super(JMESPathExtractor, JMESPathExtractorGui, DslDefinition.JMES_EXTRACTOR)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = config.applyTo

        if (applyTo == 'all') {
            testElement.setScopeAll()
        } else if (applyTo == 'parent') {
            testElement.setScopeParent()
        } else if (applyTo == 'children') {
            testElement.setScopeChildren()
        } else if (applyTo == 'variable') {
            testElement.setScopeVariable(variableName)
        } else {
            testElement.setScopeParent()
        }

        testElement.defaultValue = config.defaultValue
        testElement.matchNumber = config.match
        testElement.refName = config.variable
        testElement.jmesPathExpression = config.expression
    }
}
