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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.config.gui.LoginConfigGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

final class LoginFactory extends TestElementNodeFactory {

    LoginFactory(String testElementName) {
        super(testElementName, ConfigTestElement, LoginConfigGui, false, DslDefinition.LOGIN_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.setProperty(ConfigTestElement.USERNAME, (String) readValue(config.username, ''))
        testElement.setProperty(ConfigTestElement.PASSWORD, (String) readValue(config.password, ''))
    }
}
