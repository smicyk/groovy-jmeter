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
import net.simonix.dsl.jmeter.factory.common.BodyFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.protocol.http.util.HTTPArgument

/**
 * Helper factory responsible for <code>body</code> test element. It is used to define body of <code>http</code> element.
 * <p>
 * Body can be define as inline string or as external file.
 *
 * <pre>
 * // structure of the body
 * body (
 *     file: string
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
 *             body (file: 'payload.json')
 *         }
 *     }
 * }
 * // example with inline body
 * start {
 *     plan {
 *         http {
 *             body inline: """
 *                  {
 *                      "parameter": "value"
 *                  }
 *             """
 *         }
 *         // or
 *         http {
 *             body """
 *                  {
 *                      "parameter: "value"
 *                  }
 *             """
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 */
@CompileDynamic
final class HttpBodyFactory extends BodyFactory {

    HttpBodyFactory() {
        super(DslDefinition.HTTP_BODY)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (child instanceof HTTPArgument && parent instanceof HTTPSamplerProxy) {
            parent.postBodyRaw = true
            parent.arguments.addArgument(child)
        }
    }
}
