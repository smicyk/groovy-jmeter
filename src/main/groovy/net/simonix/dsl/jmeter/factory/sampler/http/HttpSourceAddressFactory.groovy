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
package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ProxyTestElement
import net.simonix.dsl.jmeter.factory.sampler.http.model.SourceAddressTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.AjpSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement

import java.util.regex.Matcher

/**
 * The factory class responsible for building <code>source</code> element inside http element.
 *
 * <pre>
 * // element structure
 * source (
 *     // Source address
 *     address: string
 *     type: string [hostname, device, deviceIp4, deviceIp6]
 * ) {
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
class HttpSourceAddressFactory extends TestElementFactory {

    HttpSourceAddressFactory() {
        super(SourceAddressTestElement, DslDefinition.HTTP_SOURCE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.address = config.address
        testElement.type = config.type
    }

    void updateOnComplete(Object parent, Object child) {
        if ((parent instanceof HTTPSamplerBase || parent instanceof ConfigTestElement) && child instanceof SourceAddressTestElement) {
            // Source address
            String address = child.address
            String type = child.type

            Integer ipSourceType = null

            if (type != null) {
                if (type == 'hostname') {
                    ipSourceType = HTTPSamplerBase.SourceType.HOSTNAME.ordinal()
                } else if (type == 'device') {
                    ipSourceType = HTTPSamplerBase.SourceType.DEVICE.ordinal()
                } else if (type == 'deviceIp4') {
                    ipSourceType = HTTPSamplerBase.SourceType.DEVICE_IPV4.ordinal()
                } else if (type == 'deviceIp6') {
                    ipSourceType = HTTPSamplerBase.SourceType.DEVICE_IPV6.ordinal()
                }
            }

            if(parent instanceof HTTPSamplerBase && !(parent instanceof AjpSampler)) {
                HTTPSamplerBase sampler = parent as HTTPSamplerBase

                sampler.ipSource = address
                sampler.ipSourceType = ipSourceType
            } else if(parent instanceof  ConfigTestElement) {
                ConfigTestElement testElement = parent as ConfigTestElement

                testElement.setProperty(HTTPSampler.IP_SOURCE, address)
                testElement.setProperty(HTTPSampler.IP_SOURCE_TYPE, ipSourceType)
            }
        }
    }
}
