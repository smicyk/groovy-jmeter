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
package net.simonix.dsl.jmeter.factory.postprocessor.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.jdbc.JdbcSamplerFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.processor.JDBCPostProcessor
import org.apache.jmeter.testbeans.gui.TestBeanGUI

/**
 * The factory class responsible for building <code>jdbc_postprocessor</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * jdbc_postprocessor (
 *     use: string
 * ) {
 *     {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcAutocommitFactory autocommit} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCommitFactory commit} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRollbackFactory rollback} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCallableFactory callable} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcExecuteFactory execute} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcQueryFactory query} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcParametersFactory params}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             jdbc use: 'postgres', {
 *                 jdbc_postprocessor use: 'postgres', {
 *                     execute('''
 *                         DROP TABLE employee
 *                     ''')
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JDBC_PostProcessor">JDBC PostProcessor</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 * @see net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRequestFactory JdbcRequestFactory
 */
@CompileDynamic
final class JdbcPostprocessorFactory extends JdbcSamplerFactory {

    JdbcPostprocessorFactory() {
        super(JDBCPostProcessor, TestBeanGUI, DslDefinition.JDBC_POSTPROCESSOR)
    }
}
