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
import net.simonix.dsl.jmeter.factory.config.jdbc.model.ValidationTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jmeter.testelement.TestElement

@CompileDynamic
final class JdbcValidationFactory extends TestElementFactory {

    JdbcValidationFactory() {
        super(ValidationTestElement, DslDefinition.JDBC_VALIDATION)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.idle = config.idle
        testElement.timeout = config.timeout
        testElement.query = config.query.trim()
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof DataSourceElement && child instanceof ValidationTestElement) {
            DataSourceElement dataSourceElement = parent as DataSourceElement

            dataSourceElement.keepAlive = child.idle
            dataSourceElement.timeout = child.timeout
            dataSourceElement.checkQuery = child.query

            dataSourceElement.setProperty('keepAlive', child.idle as Boolean)
            dataSourceElement.setProperty('timeout', child.timeout as String)
            dataSourceElement.setProperty('checkQuery', child.query as String)
        }
    }
}