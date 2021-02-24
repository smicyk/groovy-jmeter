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
package net.simonix.dsl.jmeter.factory.timer

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.AbstractCompositeTestElementNodeFactory
import net.simonix.dsl.jmeter.factory.AbstractTestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * General factory class responsible for creating child test elements for throughput command.
 *
 * <pre>
 * # available commands
 * {@link net.simonix.dsl.jmeter.factory.timer.ConstantThroughputFactory constant_throughput}
 * {@link net.simonix.dsl.jmeter.factory.timer.PreciseThroughputFactory precise_throughput}
 * </pre>
 */
@CompileDynamic
final class ThroughputFactory extends AbstractCompositeTestElementNodeFactory {

    ThroughputFactory() {
        super(DslDefinition.THROUGHPUT)
    }

    AbstractTestElementNodeFactory getChildFactory(FactoryBuilderSupport builder, Object name, Object value, Map config) {
        String factoryName = "${config.type}_throughput"

        return (AbstractTestElementNodeFactory) builder.factories.get(factoryName)
    }
}
