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
import net.simonix.dsl.jmeter.factory.config.jdbc.model.InitTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.config.DataSourceElement
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValues

@CompileDynamic
final class JdbcInitFactory extends TestElementFactory {

    JdbcInitFactory() {
        super(InitTestElement, DslDefinition.JDBC_INIT)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String queryValue = readValues(value, '\n', config.inline)

        String file = config.file
        if (file != null) {
            queryValue = loadFromFile(file, 'UTF-8')
        }

        testElement.query = queryValue
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof DataSourceElement && child instanceof InitTestElement) {
            DataSourceElement dataSourceElement = parent as DataSourceElement

            dataSourceElement.initQuery = child.query

            dataSourceElement.setProperty('initQuery', child.query as String)
        }
    }

    private String loadFromFile(String file, String encoding) {
        File query = null

        URL url = this.class.getResource(file)

        if(url != null) {
            query = new File(url.toURI())
        } else {
            query = new File(file)
        }

        if(query.exists()) {
            return query.getText(encoding)
        }

        throw new FileNotFoundException('''The file doesn't exist''')
    }
}
