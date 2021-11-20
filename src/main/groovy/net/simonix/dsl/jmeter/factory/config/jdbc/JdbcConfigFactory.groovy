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
package net.simonix.dsl.jmeter.factory.config.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>jdbc_config</code> element in the test.
 *
 * <pre>
 * // element structure (short variant)
 * jdbc (
 *     datasource: string
 * ) {
 *     {@link JdbcConnectionFactory connection}
 *     {@link JdbcInitFactory init}
 *     {@link JdbcPoolFactory pool}
 *     {@link JdbcValidationFactory validation}
 * }
 *
 * // element structure (long variant)
 * jdbc_config (
 *     datasource: string
 * ) {
 *     {@link JdbcConnectionFactory connection} | {@link JdbcInitFactory init} | {@link JdbcPoolFactory pool} | {@link JdbcValidationFactory validation}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             jdbc datasource: 'postgres', {
 *                 pool connections: 10, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
 *                 connection url: 'jdbc:postgresql://database-db:5432/', driver: 'org.postgresql.Driver', username: 'postgres', password: 'postgres'
 *                 init(["SET @USER = 'Joe'"])
 *                 validation idle: true, timeout: 5000, query: '''SELECT 1'''
 *             }
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JDBC_Connection_Configuration">JDBC Connection Configuration</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 */
@CompileDynamic
final class JdbcConfigFactory extends TestElementNodeFactory {

    JdbcConfigFactory() {
        super(DataSourceElement, TestBeanGUI, DslDefinition.JDBC_CONFIG)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.dataSource = config.datasource

        testElement.setProperty('dataSource', config.datasource as String)
    }
}
