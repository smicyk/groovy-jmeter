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
package net.simonix.dsl.jmeter.factory.config

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui
import org.apache.jmeter.protocol.http.sampler.HTTPSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerFactory
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty

/**
 * The factory class responsible for building <code>ajp</code> element in the test.
 *
 * <pre>
 * // element structure
 * defaults (
 *     method: string
 *     protocol: string
 *     domain: string
 *     path: string
 *     port: integer
 *
 *     // Request configuration
 *     encoding: string
 *     autoRedirects: boolean [<strong>false</strong>]
 *     followRedirects: boolean [<strong>true</strong>]
 *     keepAlive: boolean [<strong>true</strong>]
 *     multipart: boolean [<strong>false</strong>]
 *     browserCompatibleMultipart: boolean [<strong>false</strong>]
 *
 *     // implementation
 *     impl: string [<strong>java</strong>, http]
 *
 *     // Use md5 configuration
 *     saveAsMD5: boolean [<strong>false</strong>]
 * ) {
 *     {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsBodyFactory body} | {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsParamsFactory params} | {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsResourcesFactory resources} | {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsProxyFactory proxy} | {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsTimeoutFactory timeout} | {@link net.simonix.dsl.jmeter.factory.config.http.DefaultsSourceFactory source}
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>
 *
 * @see net.simonix.dsl.jmeter.factory.sampler.BaseHttpFactory BaseHttpFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DefaultsFactory extends TestElementNodeFactory {

    DefaultsFactory() {
        super(ConfigTestElement, HttpDefaultsGui, DslDefinition.DEFAULTS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // Web Server configuration
        testElement.setProperty(HTTPSampler.PROTOCOL, config.protocol as String)
        testElement.setProperty(HTTPSampler.DOMAIN, config.domain as String)
        testElement.setProperty(HTTPSampler.PORT, config.port as String)

        // Request configuration
        testElement.setProperty(HTTPSampler.METHOD, config.method as String)
        testElement.setProperty(HTTPSampler.PATH, config.path as String)
        testElement.setProperty(HTTPSampler.CONTENT_ENCODING, config.encoding as String)

        // Impl configuration
        String impl = config.impl

        if (impl != null) {
            if (impl == 'java') {
                testElement.setProperty(HTTPSampler.IMPLEMENTATION, HTTPSamplerFactory.IMPL_JAVA)
            } else if (impl == 'http') {
                testElement.setProperty(HTTPSampler.IMPLEMENTATION, HTTPSamplerFactory.IMPL_HTTP_CLIENT4)
            }
        }

        // Use md5 configuration
        testElement.setProperty(HTTPSamplerBase.MD5, config.saveAsMD5 as Boolean)

        // must provide default arguments
        testElement.property = new TestElementProperty(HTTPSampler.ARGUMENTS, new Arguments())
    }
}
