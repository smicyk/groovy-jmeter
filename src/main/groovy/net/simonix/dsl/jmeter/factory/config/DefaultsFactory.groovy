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
        testElement.setProperty(HTTPSampler.PROTOCOL, config.protocol as String)
        testElement.setProperty(HTTPSampler.DOMAIN, config.domain as String)
        testElement.setProperty(HTTPSampler.PORT, config.port as Integer)

        // Request configuration
        testElement.setProperty(HTTPSampler.METHOD, config.method as String)
        testElement.setProperty(HTTPSampler.PATH, config.path as String)
        testElement.setProperty(HTTPSampler.CONTENT_ENCODING, config.encoding as String)

        // Impl configuration
        testElement.setProperty(HTTPSampler.IMPLEMENTATION, config.impl as String)

        // Timeouts
        testElement.setProperty(HTTPSampler.CONNECT_TIMEOUT, config.connectTimeout as String)
        testElement.setProperty(HTTPSampler.RESPONSE_TIMEOUT, config.responseTimeout as String)

        // Embedded resource
        testElement.setProperty(HTTPSampler.IMAGE_PARSER, config.downloadEmbeddedResources as Boolean)
        testElement.setProperty(HTTPSampler.CONCURRENT_DWN, config.embeddedConcurrent as Boolean)
        testElement.setProperty(HTTPSampler.CONCURRENT_POOL, config.embeddedConcurrentDownloads as Integer)
        testElement.setProperty(HTTPSampler.EMBEDDED_URL_RE, config.embeddedResourceUrl as String)

        // Source address
        testElement.setProperty(HTTPSampler.IP_SOURCE, config.ipSource as String)
        String ipSourceType = config.ipSourceType as String

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
        testElement.setProperty(HTTPSamplerBase.PROXYSCHEME, config.proxySchema as String)
        testElement.setProperty(HTTPSamplerBase.PROXYHOST, config.proxyHost as String)
        testElement.setProperty(HTTPSamplerBase.PROXYPORT, config.proxyPort as String)
        testElement.setProperty(HTTPSamplerBase.PROXYUSER, config.proxyUser as String)
        testElement.setProperty(HTTPSamplerBase.PROXYPASS, config.proxyPassword as String)

        // Use md5 configuration
        testElement.setProperty(HTTPSamplerBase.MD5, config.saveAsMD5 as Boolean)

        // must provide default arguments
        testElement.property = new TestElementProperty(HTTPSampler.ARGUMENTS, new Arguments())
    }
}
