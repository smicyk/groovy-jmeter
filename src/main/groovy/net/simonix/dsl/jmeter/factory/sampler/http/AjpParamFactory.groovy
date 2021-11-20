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
import net.simonix.dsl.jmeter.factory.common.ParamFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * Builds the single parameter for test element. It is used with conjunction with <code>ajp</code> elements.
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
 *         ajp {
 *             params {
 *                 param(name: 'param1', value: 'value1', encoding: 'UTF-8')
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.AjpFactory AjpFactory
 */
@CompileDynamic
final class AjpParamFactory extends ParamFactory {

    AjpParamFactory() {
        super(DslDefinition.AJP_PARAM)
    }
}
