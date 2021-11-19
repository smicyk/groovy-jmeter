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
package net.simonix.dsl.jmeter.factory.sampler.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.model.ParameterTestElement
import net.simonix.dsl.jmeter.factory.sampler.jdbc.model.ParametersTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testelement.TestElement

/**
 * Builds the single parameter for test element. It is used with conjunction with <code>query</code> elements.
 *
 * <pre>
 * // structure of the param
 * param (
 *     type: string
 *     value: string
 * )
 * // example usage
 * start {
 *     plan {
 *         jdbc use: 'postgres', {
*              execute('''
*                 UPDATE employee SET salary = ? WHERE id = ?
*              ''') {
*                 params {
*                     param value: '1000', type: 'IN INTEGER'
*                     param value: '1', type: 'IN INTEGER'
*                 }
*              }
*          }
 *     }
 * }
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 */
@CompileDynamic
class JdbcParameterFactory extends TestElementFactory {

    JdbcParameterFactory() {
        super(ParameterTestElement, DslDefinition.JDBC_PARAMETER)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.value = config.value
        testElement.type = config.type
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof ParametersTestElement && child instanceof ParameterTestElement) {
            parent.add(child)
        }
    }
}
