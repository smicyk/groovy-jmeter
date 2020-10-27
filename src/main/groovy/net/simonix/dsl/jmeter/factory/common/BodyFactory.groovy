/*
 * Copyright 2019 Szymon Micyk
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

import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.sampler.AjpSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Helper factory responsible for <code>body</code> test element. It is used to define body of <code>http</code> or <code>ajp</code> element.
 * <p>
 * Body can be define as inline string or as external file.
 *
 * <pre>
 * // structure of the body
 * body (
 *     path: string
 *     encoding: string [<strong>UTF-8</strong>]
 * )
 * // or
 * body """
 *   // inline string with body content
 * """
 * // example with body content in file
 * start {
 *     plan {
 *         http {
 *             body (path: 'payload.json')
 *         }
 *     }
 * }
 * // example with inline body
 * start {
 *     plan {
 *         http {
 *             body """
 *                  {
 *                      "parameter": "value"
 *                  }
 *             """
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.AjpFactory AjpFactory
 */
final class BodyFactory extends TestElementFactory {

    BodyFactory() {
        super(HTTPArgument, DslDefinition.BODY_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String bodyValue = value

        String path = config.path
        if (path != null) {
            String encoding = readValue(config.encoding, 'UTF-8')
            bodyValue = loadFromFile(path, encoding)
        }

        testElement.name = ''
        testElement.value = bodyValue
        testElement.metaData = '='
        testElement.alwaysEncoded = false
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (child instanceof HTTPArgument) {
            if (parent instanceof HTTPSamplerProxy) {
                parent.postBodyRaw = true
                parent.arguments.addArgument(child)
            } else if (parent instanceof AjpSampler) {
                parent.postBodyRaw = true
                parent.arguments.addArgument(child)
            }
        }
    }

    private String loadFromFile(String path, String encoding) {
        return new File(path).getText(encoding)
    }
}
