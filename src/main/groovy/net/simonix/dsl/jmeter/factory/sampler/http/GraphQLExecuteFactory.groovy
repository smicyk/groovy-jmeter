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
package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ExecuteTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.config.gui.GraphQLUrlConfigGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.loadFromFile
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class GraphQLExecuteFactory extends TestElementFactory {

    GraphQLExecuteFactory() {
        super(ExecuteTestElement, DslDefinition.GRAPHQL_EXECUTE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String content = readValue(value, config.inline)

        String file = config.file
        if (file != null) {
            content = loadFromFile(file, 'UTF-8')
        }

        testElement.content = content
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof HTTPSamplerBase && child instanceof ExecuteTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            sampler.setProperty(GraphQLUrlConfigGui.QUERY, child.content)
        }
    }
}