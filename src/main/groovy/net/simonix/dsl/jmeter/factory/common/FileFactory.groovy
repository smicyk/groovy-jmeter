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
package net.simonix.dsl.jmeter.factory.common

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import org.apache.jmeter.protocol.http.util.HTTPFileArg
import org.apache.jmeter.protocol.http.util.HTTPFileArgs
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Builds the single file for test element. It is used with conjunction with <code>http</code> or <code>ajp</code> elements.
 *
 * <pre>
 * // structure of the file
 * file (
 *     file: string
 *     name: string
 *     type: string
 * )
 * // example usage
 * start {
 *     plan {
 *         http {
 *             files {
 *                 file 'test1.txt', name: 'param1', type: 'text/plain'
 *                 file 'test2.txt', name: 'param2', type: 'text/plain'
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.AjpFactory AjpFactory
 * @see net.simonix.dsl.jmeter.factory.common.FilesFactory FilesFactory
 */
@CompileDynamic
abstract class FileFactory extends TestElementFactory {

    FileFactory(KeywordDefinition definition) {
        super(HTTPFileArg, definition)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        String path = readValue(value, config.file)

        return new HTTPFileArg(path, config.name as String, config.type as String)
    }

    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof HTTPFileArgs && child instanceof HTTPFileArg) {
            parent.addHTTPFileArg(child)
        }
    }
}
