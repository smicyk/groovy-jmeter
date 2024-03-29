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
package net.simonix.dsl.jmeter.factory.plan

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.common.ArgumentFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * Builds the single <code>argument</code> for test element. It is used with conjunction with <code>arguments</code>.
 *
 * <pre>
 * // structure of the argument
 * argument (
 *     name: string
 *     value: string
 * )
 * // example usage
 * start {
 *     plan {
 *         arguments {
 *             argument(name: 'var1', value: 'value1')
 *         }
 *     }
 * }
 * </pre>
 *
 * @see TestElementFactory TestElementFactory
 * @see net.simonix.dsl.jmeter.factory.plan.PlanVariablesFactory PlanVariablesFactory
 * @see net.simonix.dsl.jmeter.factory.plan.PlanFactory PlanFactory
 */
@CompileDynamic
final class PlanVariableFactory extends ArgumentFactory {

    PlanVariableFactory() {
        super(DslDefinition.PLAN_ARGUMENT)
    }
}
