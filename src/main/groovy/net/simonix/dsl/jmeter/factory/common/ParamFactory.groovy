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
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Builds the single parameter for test element. It is used with conjunction with <code>http</code> or <code>defaults</code> elements.
 *
 * <pre>
 * // structure of the param
 * param (
 *     name: string
 *     value: string
 *     encoded: boolean [<strong>false</strong>]
 *     encoding: string [<strong>UTF-8</strong>]
 * )
 * // example usage
 * start {
 *     plan {
 *         http {
 *             params {
 *                 param(name: 'param1', value: 'value1', encoding: 'UTF-8')
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 * @see net.simonix.dsl.jmeter.factory.config.DefaultsFactory DefaultsFactory
 */
final class ParamFactory extends TestElementFactory {

    ParamFactory() {
        super(HTTPArgument)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        boolean isEncoded = readValue(Boolean, config.encoded, false)

        return new HTTPArgument(
                readValue(String, config.name, null),
                readValue(String, config.value, null),
                isEncoded,
                readValue(String, config.encoding, 'UTF-8')
        )
    }

    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof Arguments && child instanceof Argument) {
            parent.addArgument(child)
        }
    }
}
