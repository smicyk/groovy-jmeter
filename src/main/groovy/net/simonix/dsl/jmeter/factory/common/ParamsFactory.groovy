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

import groovy.json.JsonSlurper
import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Builds parameters for test element. It is used with conjunction with <code>http</code> or <code>defaults</code> elements.
 * <p>
 * It is shortcut for {@link ParamFactory param}.
 *
 * <pre>
 * // structure with nested argument
 * params {
 *   {@link ParamFactory param}
 * }
 * // structure of arguments
 * params (
 *    values: map
 * )
 * // example usage
 * start {
 *     plan {
 *         http {
 *             params values: [
 *                 'param1': 'value1',
 *                 'param2': 'value2'
 *             ]
 *         }
 *     }
 * }
 * </pre>
 *
 * @see ParamFactory ParamFactory
 */
@CompileDynamic
final class ParamsFactory extends TestElementFactory {

    ParamsFactory() {
        super(Arguments, DslDefinition.PARAMS.leaf, DslDefinition.PARAMS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // try to read parameters from json file
        Map<String, String> params = [:]
        String filename = readValue(value, null)
        if (filename) {
            params = readParamsFromFile(filename)
        }

        params += config.values

        // by default arguments are not encoded and has UTF-8 encoding
        params.each { k, v -> testElement.addArgument(new HTTPArgument(k, v, true, 'UTF-8')) }
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object arguments) {
        if (arguments instanceof Arguments) {
            if (parent instanceof HTTPSamplerBase) {
                parent.arguments = arguments
            } else if (parent instanceof ConfigTestElement) {
                parent.getProperty(HTTPSamplerBase.ARGUMENTS).objectValue = arguments
            }
        }
    }

    private Map<String, String> readParamsFromFile(String filename) {
        JsonSlurper slurper = new JsonSlurper()
        Object json = slurper.parse(new FileReader(new File(filename)))

        Map<String, String> params = [:]
        json.each { String key, value ->
            params[key] = value.toString()
        }

        return params
    }
}
