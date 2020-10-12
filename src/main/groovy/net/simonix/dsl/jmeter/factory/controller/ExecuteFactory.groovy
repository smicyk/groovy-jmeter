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
package net.simonix.dsl.jmeter.factory.controller

import net.simonix.dsl.jmeter.factory.AbstractCompositeTestElementNodeFactory
import net.simonix.dsl.jmeter.factory.AbstractTestElementNodeFactory

/**
 * General factory class responsible for creating child test elements for execute command.
 *
 * <pre>
 * # available commands
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.IfControllerFactory execute_if}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.InterleaveControllerFactory execute_interleave}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.OnceControllerFactory execute_once}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.PercentControllerFactory execute_percent}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.RandomControllerFactory execute_random}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.RandomOrderControllerFactory execute_order}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.RuntimeControllerFactory execute_runtime}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.SwitchControllerFactory execute_switch}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.TotalControllerFactory execute_total}
 * {@link net.simonix.dsl.jmeter.factory.controller.execution.WhileControllerFactory execute_while}
 * </pre>
 */
final class ExecuteFactory extends AbstractCompositeTestElementNodeFactory {

    AbstractTestElementNodeFactory getChildFactory(FactoryBuilderSupport builder, Object name, Object value, Map config) {
        String factoryName = "execute_${config.type}"

        return (AbstractTestElementNodeFactory) builder.getFactories().get(factoryName)
    }
}
