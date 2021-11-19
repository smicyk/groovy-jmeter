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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.model.ConnectionTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>connection</code> element for jdbc configuration.
 *
 * <pre>
 * // structure of the element
 * connection (
 *     url: string
 *     driver: string
 *     username: string
 *     password: string
 *     properties: map
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             jdbc datasource: 'postgres', {
 *                 connection url: 'jdbc:postgresql://database-db:5432/', driver: 'org.postgresql.Driver', username: 'postgres', password: 'postgres', properties: ['ssl': 'true']
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 * @see ConnectionTestElement ConnectionTestElement
 * @see JdbcConfigFactory JdbcConfigFactory
 */
@CompileDynamic
final class JdbcConnectionFactory extends TestElementFactory {

    JdbcConnectionFactory() {
        super(ConnectionTestElement, DslDefinition.JDBC_CONNECTION)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.url = config.url
        testElement.driver = config.driver
        testElement.username = config.username
        testElement.password = config.password

        String properties = config.properties.collect {"$it.key=$it.value" }.join(';')
        testElement.properties = properties
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof DataSourceElement && child instanceof ConnectionTestElement) {
            DataSourceElement dataSourceElement = parent as DataSourceElement

            dataSourceElement.dbUrl = child.url
            dataSourceElement.driver = child.driver
            dataSourceElement.username = child.username
            dataSourceElement.password = child.password
            dataSourceElement.connectionProperties = child.properties

            dataSourceElement.setProperty('dbUrl', child.url)
            dataSourceElement.setProperty('driver', child.driver)
            dataSourceElement.setProperty('username', child.username)
            dataSourceElement.setProperty('password', child.password)
            dataSourceElement.setProperty('connectionProperties', child.properties)
        }
    }
}
