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
package net.simonix.dsl.jmeter.factory.sampler

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerFactory
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.BooleanProperty
import org.apache.jmeter.testelement.property.TestElementProperty

/**
 * The factory class responsible for building <code>http</code> element in the test.
 *
 * <pre>
 * // element structure
 * http (
 *     method: string
 *     protocol: string
 *     domain: string
 *     path: string
 *     port: integer
 *     // Request configuration
 *     encoding: string
 *     autoRedirects: boolean [<strong>false</strong>]
 *     followRedirects: boolean [<strong>true</strong>]
 *     keepAlive: boolean [<strong>true</strong>]
 *     multipart: boolean [<strong>false</strong>]
 *     browserCompatibleMultipart: boolean [<strong>false</strong>]
 *
 *     // Impl configuration
 *     impl: string
 *
 *     // Use md5 configuration
 *     saveAsMD5: boolean [<strong>false</strong>]
 * )
 * {
 *    body | params | headers | timeout | source | resources | proxy
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class HttpFactory extends BaseHttpFactory {

    HttpFactory() {
        super(HTTPSamplerProxy, HttpTestSampleGui, DslDefinition.HTTP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        super.updateTestElementProperties(testElement, name, value, config)

        // Request configuration
        testElement.doMultipartPost = config.multipart
        testElement.doBrowserCompatibleMultipart = config.browserCompatibleMultipart

        // Impl configuration
        String impl = config.impl

        if(impl != null) {
            if(impl == 'java') {
                testElement.implementation = HTTPSamplerFactory.IMPL_JAVA
            } else if(impl == 'http') {
                testElement.implementation = HTTPSamplerFactory.IMPL_HTTP_CLIENT4
            }
        }


        // Use md5 configuration
        testElement.MD5 = config.saveAsMD5
    }
}
