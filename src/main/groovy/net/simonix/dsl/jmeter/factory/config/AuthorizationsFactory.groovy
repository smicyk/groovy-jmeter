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

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.control.AuthManager
import org.apache.jmeter.protocol.http.gui.AuthPanel

/**
 * The factory class responsible for building <code>authorizations</code> element in the test.
 *
 * <pre>
 * // element structure
 * authorizations {
 *     {@link AuthorizationFactory authorization}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Authorization_Manager">HTTP Authorization Manager</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see AuthorizationFactory AuthorizationFactory
 */
@CompileDynamic
final class AuthorizationsFactory extends TestElementNodeFactory {

    AuthorizationsFactory(String testElementName) {
        super(testElementName, AuthManager, AuthPanel, false, DslDefinition.AUTHORIZATIONS_PROPERTIES)
    }
}
