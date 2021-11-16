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
import net.simonix.dsl.jmeter.factory.sampler.jdbc.model.QueryTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.AbstractJDBCTestElement
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.loadFromFile
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
class JdbcCallableFactory extends TestElementFactory {

    JdbcCallableFactory() {
        super(QueryTestElement, DslDefinition.JDBC_CALLABLE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.type = 'Callable Statement'
        testElement.timeout = config.timeout

        testElement.result = config.result
        testElement.handler = mapResultHandler(config.handler as String)
        testElement.limit = config.limit

        String query = readValue(value, config.inline)

        if(!query) {
            query = loadFromFile(config.file as String, 'UTF-8')
        }

        testElement.query = query.stripIndent()
        testElement.variables = config.variables
    }

    void updateOnComplete(Object parent, Object child) {
        if (parent instanceof AbstractJDBCTestElement && child instanceof QueryTestElement) {
            AbstractJDBCTestElement sampler = parent as AbstractJDBCTestElement

            sampler.queryType = child.type
            sampler.queryTimeout = child.timeout

            sampler.resultVariable = child.result
            sampler.resultSetHandler = child.handler
            sampler.resultSetMaxRows = child.limit
            sampler.query = child.query
            sampler.variableNames = child.variables

            if(child.parameters) {
                sampler.queryArguments = child.parameters.elements.collect { "$it.value" }.join(',')
                sampler.queryArgumentsTypes = child.parameters.elements.collect { "$it.type" }.join(',')
            }

            sampler.setProperty('queryType', child.type as String)
            sampler.setProperty('queryTimeout', child.timeout as String)
            sampler.setProperty('resultVariable', child.result as String)
            sampler.setProperty('resultSetHandler', child.handler as String)
            sampler.setProperty('resultSetMaxRows', child.limit as String)
            sampler.setProperty('query', child.query as String)
            sampler.setProperty('variableNames', child.variables as String)
            sampler.setProperty('queryArguments', sampler.queryArguments)
            sampler.setProperty('queryArgumentsTypes', sampler.queryArgumentsTypes)
        }
    }

    private String mapResultHandler(String handler) {
        String result = 'Store as String'

        switch (handler) {
            case 'string':
                result = 'Store as String'
                break
            case 'object':
                result = 'Store as Object'
                break
            case 'count':
                result = 'Count Records'
                break
            default:
                result = 'Store as String'
                break
        }

        return result
    }
}
