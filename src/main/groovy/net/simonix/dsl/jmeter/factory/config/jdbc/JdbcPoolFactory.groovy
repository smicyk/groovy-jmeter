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
import net.simonix.dsl.jmeter.factory.config.jdbc.model.PoolTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jmeter.testelement.TestElement

@CompileDynamic
final class JdbcPoolFactory extends TestElementFactory {

    JdbcPoolFactory() {
        super(PoolTestElement, DslDefinition.JDBC_POOL)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.connections = config.connections
        testElement.wait = config.wait
        testElement.eviction = config.eviction
        testElement.autocommit = config.autocommit
        testElement.isolation = config.isolation
        testElement.preinit = config.preinit
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof DataSourceElement && child instanceof PoolTestElement) {
            DataSourceElement dataSourceElement = parent as DataSourceElement

            dataSourceElement.poolMax = child.connections as String
            dataSourceElement.trimInterval = child.eviction as String
            dataSourceElement.timeout = child.wait
            dataSourceElement.autocommit = child.autocommit
            dataSourceElement.transactionIsolation = child.isolation
            dataSourceElement.preinit = child.preinit
            dataSourceElement.connectionAge = 1000

            dataSourceElement.setProperty('poolMax', child.connections as String)
            dataSourceElement.setProperty('trimInterval', child.eviction as String)
            dataSourceElement.setProperty('timeout', child.wait as String)
            dataSourceElement.setProperty('autocommit', child.autocommit as Boolean)
            dataSourceElement.setProperty('transactionIsolation', child.isolation as String)
            dataSourceElement.setProperty('preinit', child.preinit as Boolean)
            dataSourceElement.setProperty('connectionAge', 1000)
        }
    }
}
