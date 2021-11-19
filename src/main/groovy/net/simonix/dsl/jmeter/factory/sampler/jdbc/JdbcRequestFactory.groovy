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
package net.simonix.dsl.jmeter.factory.sampler.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.jdbc.JdbcSamplerFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.sampler.JDBCSampler
import org.apache.jmeter.testbeans.gui.TestBeanGUI

/**
 * The factory class responsible for building <code>jdbc_request</code> element in the test.
 *
 * <pre>
 * // element structure (short variant)
 * jdbc (
 *     use: string
 * ) {
 *     {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcAutocommitFactory autocommit} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCommitFactory commit} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRollbackFactory rollback} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCallableFactory callable} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcExecuteFactory execute} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcQueryFactory query} | {@link net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcParametersFactory params}
 * }
 *
 * // element structure (long variant)
 * jdbc_request (
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
 *                 execute('''
 *                     CREATE TABLE employee (id INTEGER PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(50), salary INTEGER)
 *                 ''')
 *             }
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#JDBC_Request">JDBC Request</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementFactory TestElementFactory
 */
@CompileDynamic
class JdbcRequestFactory extends JdbcSamplerFactory {

    JdbcRequestFactory() {
        super(JDBCSampler, TestBeanGUI, DslDefinition.JDBC_REQUEST)
    }
}
