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
package net.simonix.dsl.jmeter.factory.listener

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.ArgumentsFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.visualizers.backend.BackendListener

/**
 * Builds arguments for test element. It is used with conjunction with <code>backend</code> listener.
 *
 * <pre>
 * // structure with nested argument
 * arguments {
 *     {@link net.simonix.dsl.jmeter.factory.listener.BackendArgumentFactory argument}
 * }
 *
 * // structure without nested argument
 * arguments (
 *    values: map
 * )
 *
 * // example usage
 * start {
 *     plan {
 *         backend {
 *             arguments values: [
 *                 'var1': 'value1',
 *                 'var2': 'value2'
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.listener.BackendArgumentFactory BackendArgumentFactory
 */
@CompileDynamic
final class BackendArgumentsFactory extends ArgumentsFactory {

    BackendArgumentsFactory() {
        super(DslDefinition.BACKEND_ARGUMENTS)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object arguments) {
        if (arguments instanceof Arguments && parent instanceof BackendListener) {
            parent.arguments = arguments
        }
    }
}
