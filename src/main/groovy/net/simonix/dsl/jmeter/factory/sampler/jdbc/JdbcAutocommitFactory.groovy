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
package net.simonix.dsl.jmeter.factory.sampler.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.model.QueryTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.AbstractJDBCTestElement
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>autocommit</code> element in the test.
 *
 * <pre>
 * // element structure
 * autocommit (
 *     value: boolean  [<strong>true</strong>]
 * ) {

 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             jdbc use: 'postgres', {
 *                 autocommit true
 *             }
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JDBC_Request">JDBC Request</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 * @see JdbcRequestFactory JdbcRequestFactory
 */
@CompileDynamic
class JdbcAutocommitFactory extends TestElementFactory {

    JdbcAutocommitFactory() {
        super(QueryTestElement, DslDefinition.JDBC_AUTOCOMMIT)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean autocommit = readValue(value, config.value)

        if (autocommit) {
            testElement.type = 'AutoCommit(true)'
        } else {
            testElement.type = 'AutoCommit(false)'
        }
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof AbstractJDBCTestElement && child instanceof QueryTestElement) {
            AbstractJDBCTestElement sampler = parent as AbstractJDBCTestElement

            sampler.queryType = child.type

            sampler.setProperty('queryType', child.type as String)
        }
    }
}
