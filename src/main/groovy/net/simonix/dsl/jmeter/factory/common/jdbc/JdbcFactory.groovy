/*
 * Copyright 2022 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.common.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.AbstractCompositeTestElementNodeFactory
import net.simonix.dsl.jmeter.factory.AbstractTestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition

/**
 * Composite JDBC Factory. Depending on <strong>datasource</strong> parameter it create {@link net.simonix.dsl.jmeter.factory.config.jdbc.JdbcConfigFactory} or {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRequestFactory}.
 */
@CompileDynamic
final class JdbcFactory extends AbstractCompositeTestElementNodeFactory {

    JdbcFactory() {
        super(DslDefinition.JDBC)
    }

    AbstractTestElementNodeFactory getChildFactory(FactoryBuilderSupport builder, Object name, Object value, Map config) {
        if (config.datasource) {
            return (AbstractTestElementNodeFactory) builder.factories.get('jdbc_config')
        } else {
            return (AbstractTestElementNodeFactory) builder.factories.get('jdbc_request')
        }
    }
}
