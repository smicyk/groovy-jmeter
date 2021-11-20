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
package net.simonix.dsl.jmeter.factory.config.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.sampler.http.AbstractProxyFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ProxyTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase

/**
 * The factory class responsible for building <code>proxy</code> element inside <strong>defaults</strong> element.
 *
 * <pre>
 * // element structure
 * proxy (
 *     schema: string
 *     host: string
 *     port: int
 *     username: string
 *     password: string
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>
 *
 * @see net.simonix.dsl.jmeter.factory.config.DefaultsFactory DefaultsFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DefaultsProxyFactory extends AbstractProxyFactory {

    DefaultsProxyFactory() {
        super(DslDefinition.DEFAULTS_PROXY)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof ConfigTestElement && child instanceof ProxyTestElement) {
            ConfigTestElement testElement = parent as ConfigTestElement

            testElement.setProperty(HTTPSamplerBase.PROXYSCHEME, child.scheme)
            testElement.setProperty(HTTPSamplerBase.PROXYHOST, child.host)
            testElement.setProperty(HTTPSamplerBase.PROXYPORT, child.port)
            testElement.setProperty(HTTPSamplerBase.PROXYUSER, child.username)
            testElement.setProperty(HTTPSamplerBase.PROXYPASS, child.password)
        }
    }
}
