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
import org.apache.http.client.config.CookieSpecs
import org.apache.jmeter.protocol.http.control.CookieManager
import org.apache.jmeter.protocol.http.gui.CookiePanel
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>cookies</code> element in the test.
 *
 * <pre>
 * // element structure
 * cookies (
 *   clearEachIteration: boolean [<strong>true</strong>]
 *   policy: string [<strong>standard</strong>, compatibility, netscape, standard-strict, best-match, default, ignoreCookies]
 * ) {
 *     {@link CookieFactory cookie}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cookie_Manager">HTTP Cookie Manager</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see CookieFactory CookieFactory
 */
final class CookiesFactory extends TestElementNodeFactory {

    CookiesFactory(String testElementName) {
        super(testElementName, CookieManager, CookiePanel, false, DslDefinition.COOKIES_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean clearEachIteration = readValue(config.clearEachIteration, true)

        testElement.clearEachIteration = clearEachIteration
        testElement.cookiePolicy = readValue(config.policy, CookieSpecs.STANDARD)

        // cookie policies: compatibility, standard, netscape, standard-strict, best-match, default, ignoreCookies
    }
}
