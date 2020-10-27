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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.control.Cookie
import org.apache.jmeter.protocol.http.control.CookieManager
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>cookie</code> element in the test.
 *
 * <pre>
 * // element structure
 * cookie (
 *   name: string
 *   value: string
 *   domain: string
 *   path: string
 *   secure: boolean [<strong>false</strong>]
 *   expires: integer [<strong>0</strong>]
 * )
 * // sample usage
 * start {
 *     plan {
 *         cookies {
 *             cookie(secure: true, path: '/context', domain: 'localhost', name: 'JSESSIONID', value: '223EFE34432DFDF', expires: 1000)
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cookie_Manager">HTTP_Cookie_Manager</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 * @see CookiesFactory CookiesFactory
 */
@CompileDynamic
final class CookieFactory extends TestElementFactory {

    CookieFactory() {
        super(Cookie, DslDefinition.COOKIE_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean secure = readValue(config.secure, false)
        boolean pathSpecified = config.path != null && config.path != ''
        boolean domainSpecified = config.domain != null && config.domain != ''

        testElement.name = readValue(config.name, '')
        testElement.value = readValue(config.value, '')
        testElement.domain = readValue(config.domain, '')
        testElement.path = readValue(config.path, '')
        testElement.secure = secure
        testElement.expires = readValue(config.expires, 0)
        testElement.pathSpecified = pathSpecified
        testElement.domainSpecified = domainSpecified
        testElement.version = 1
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object manager, Object cookie) {
        if (manager instanceof CookieManager && cookie instanceof Cookie) {
            manager.add(cookie)
        }
    }
}
