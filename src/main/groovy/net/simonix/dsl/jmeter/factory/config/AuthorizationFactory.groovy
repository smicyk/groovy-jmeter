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
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.AuthManager
import org.apache.jmeter.protocol.http.control.AuthManager.Mechanism
import org.apache.jmeter.protocol.http.control.Authorization
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>authorization</code> element in the test.
 * <p>
 * It is used as child element of <code>authorizations</code> test element.
 *
 * <pre>
 * // element structure
 * authorization (
 *     url: string
 *     username: string
 *     password: string
 *     domain: string
 *     realm: string
 *     mechanism: string [<strong>BASIC</strong>, DIGEST, KERBEROS, <s>BASIC_DIGEST</s>]
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             authorizations {
 *                 authorization(url: 'localhost', username: 'john', password: 'john')
 *             }
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Authorization_Manager">HTTP Authorization Manager</a>
 *
 * @see TestElementFactory TestElementFactory
 * @see AuthorizationsFactory AuthorizationsFactory
 */
@CompileDynamic
final class AuthorizationFactory extends TestElementFactory {

    AuthorizationFactory() {
        super(Authorization, DslDefinition.AUTHORIZATION)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.URL = config.url
        testElement.user = config.username
        testElement.pass = config.password
        testElement.domain = config.domain
        testElement.realm = config.realm
        testElement.mechanism = Mechanism.valueOf(config.mechanism as String)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof AuthManager && child instanceof Authorization) {
            parent.addAuth(child)
        }
    }
}
