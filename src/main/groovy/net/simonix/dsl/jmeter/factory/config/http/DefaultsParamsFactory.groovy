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
package net.simonix.dsl.jmeter.factory.config.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.ParamsFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase

/**
 * Builds parameters for test element. It is used with conjunction with <code>defaults</code> elements.
 * <p>
 * It is shortcut for {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsParamFactory param}.
 *
 * <pre>
 * // structure with nested argument
 * params {
 *   {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsParamFactory param}
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
 * @see net.simonix.dsl.jmeter.factory.config.http.DefaultsParamFactory DefaultsParamFactory
 */
@CompileDynamic
final class DefaultsParamsFactory extends ParamsFactory {

    DefaultsParamsFactory() {
        super(DslDefinition.DEFAULTS_PARAMS)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object arguments) {
        if (arguments instanceof Arguments && parent instanceof ConfigTestElement) {
            parent.getProperty(HTTPSamplerBase.ARGUMENTS).objectValue = arguments
        }
    }
}
