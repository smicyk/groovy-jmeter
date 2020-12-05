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
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui
import org.apache.jmeter.protocol.http.sampler.HTTPSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
final class DefaultsFactory extends TestElementNodeFactory {

    DefaultsFactory(String testElementName) {
        super(testElementName, ConfigTestElement, HttpDefaultsGui, false, DslDefinition.DEFAULTS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        // Web Server configuration
        testElement.setProperty(HTTPSampler.PROTOCOL, readValue(String, config.protocol, 'http'))
        testElement.setProperty(HTTPSampler.DOMAIN, readValue(String, config.domain, ''))
        testElement.setProperty(HTTPSampler.PORT, readValue(Integer, config.port, 80))

        // Request configuration
        testElement.setProperty(HTTPSampler.METHOD, readValue(String, config.method, 'GET'))
        testElement.setProperty(HTTPSampler.PATH, readValue(String, config.path, ''))
        testElement.setProperty(HTTPSampler.CONTENT_ENCODING, readValue(String, config.encoding, ''))

        // Impl configuration
        testElement.setProperty(HTTPSampler.IMPLEMENTATION, readValue(String, config.impl, ''))

        // Timeouts
        testElement.setProperty(HTTPSampler.CONNECT_TIMEOUT, readValue(String, config.connectTimeout, ''))
        testElement.setProperty(HTTPSampler.RESPONSE_TIMEOUT, readValue(String, config.responseTimeout, ''))

        // Embedded resource
        testElement.setProperty(HTTPSampler.IMAGE_PARSER, readValue(Boolean, config.downloadEmbeddedResources, false))
        testElement.setProperty(HTTPSampler.CONCURRENT_DWN, readValue(Boolean, config.embeddedConcurrent, false))
        testElement.setProperty(HTTPSampler.CONCURRENT_POOL, readValue(Integer, config.embeddedConcurrentDownloads, 6))
        testElement.setProperty(HTTPSampler.EMBEDDED_URL_RE, readValue(String, config.embeddedResourceUrl, ''))

        // Source address
        testElement.setProperty(HTTPSampler.IP_SOURCE, readValue(String, config.ipSource, ''))
        String ipSourceType = readValue(String, config.ipSourceType, null)

        if(ipSourceType != null) {
            if(ipSourceType == 'hostname') {
                testElement.setProperty(HTTPSampler.IP_SOURCE_TYPE, HTTPSamplerBase.SourceType.HOSTNAME.ordinal())
            } else if(ipSourceType == 'device') {
                testElement.setProperty(HTTPSampler.IP_SOURCE_TYPE, HTTPSamplerBase.SourceType.DEVICE.ordinal())
            } else if(ipSourceType == 'deviceIp4') {
                testElement.setProperty(HTTPSampler.IP_SOURCE_TYPE, HTTPSamplerBase.SourceType.DEVICE_IPV4.ordinal())
            } else if(ipSourceType == 'deviceIp6') {
                testElement.setProperty(HTTPSampler.IP_SOURCE_TYPE, HTTPSamplerBase.SourceType.DEVICE_IPV6.ordinal())
            }
        }

        // Proxy configuration
        testElement.setProperty(HTTPSamplerBase.PROXYSCHEME, readValue(String, config.proxySchema, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYHOST, readValue(String, config.proxyHost, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYPORT, readValue(String, config.proxyPort, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYUSER, readValue(String, config.proxyUser, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYPASS, readValue(String, config.proxyPassword, ''))

        // Use md5 configuration
        testElement.setProperty(HTTPSamplerBase.MD5, readValue(Boolean, config.saveAsMD5, false))

        // must provide default arguments
        testElement.property = new TestElementProperty(HTTPSampler.ARGUMENTS, new Arguments())
    }
}
