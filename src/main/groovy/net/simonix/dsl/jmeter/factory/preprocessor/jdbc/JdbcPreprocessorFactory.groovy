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
package net.simonix.dsl.jmeter.factory.preprocessor.jdbc

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.jdbc.JdbcSamplerFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.processor.JDBCPreProcessor
import org.apache.jmeter.testbeans.gui.TestBeanGUI

@CompileDynamic
final class JdbcPreprocessorFactory extends JdbcSamplerFactory {

    JdbcPreprocessorFactory() {
        super(JDBCPreProcessor, TestBeanGUI, DslDefinition.JDBC_PREPROCESSOR)
    }
}
