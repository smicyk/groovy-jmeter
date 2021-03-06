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
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.CookieManager
import org.apache.jmeter.protocol.http.gui.CookiePanel
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>cookies</code> element in the test.
 *
 * <pre>
 * // element structure
 * cookies (
 *   clearEachIteration: boolean [<strong>true</strong>]
 *   policy: string              [<strong>standard</strong>, compatibility, netscape, standard-strict, best-match, default, ignoreCookies]
 *   useUserConfig: boolean      [<strong>false</strong>]
 * ) {
 *     {@link CookieFactory cookie}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cookie_Manager">HTTP Cookie Manager</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see CookieFactory CookieFactory
 */
@CompileDynamic
final class CookiesFactory extends TestElementNodeFactory {

    CookiesFactory() {
        super(CookieManager, CookiePanel, DslDefinition.COOKIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean clearEachIteration = config.clearEachIteration
        boolean controlledByUser = config.useUserConfig

        testElement.clearEachIteration = clearEachIteration
        testElement.cookiePolicy = config.policy
        testElement.controlledByThread = controlledByUser

        // cookie policies: compatibility, standard, netscape, standard-strict, best-match, rfc2109, rfc2965, default, ignoreCookies
    }
}
