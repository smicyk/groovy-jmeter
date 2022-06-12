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
package net.simonix.dsl.jmeter.factory.config.jdbc.model

import groovy.transform.CompileDynamic
import org.apache.jmeter.testelement.AbstractTestElement
import org.apache.jmeter.testelement.TestElement

/**
 * Container class for JDBC Connection configuration
 */
@CompileDynamic
final class ConnectionTestElement extends AbstractTestElement implements TestElement {

    String url
    String driver
    String username
    String password
    String properties
}
