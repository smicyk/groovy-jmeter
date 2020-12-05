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
package net.simonix.dsl.jmeter.factory.sampler

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

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
 *     // Timeouts
 *     connectTimeout: string
 *     responseTimeout: string
 *
 *     // Embedded resource
 *     downloadEmbeddedResources: boolean [<strong>false</strong>]
 *     embeddedConcurrent: boolean [<strong>false</strong>]
 *     embeddedConcurrentDownloads: integer [<strong>6</strong>]
 *     embeddedResourceUrl: string
 *
 *     // Source address
 *     ipSource: string
 *     ipSourceType: string [hostname, device, deviceIp4, deviceIp6]
 *
 *     // Proxy configuration
 *     proxySchema: string
 *     proxyHost: string
 *     proxyPort: string
 *     proxyUser: string
 *     proxyPassword: string
 *
 *     // Use md5 configuration
 *     saveAsMD5: boolean [<strong>false</strong>]
 * ) {*     body | params | headers
 *}* </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Critical_Section_Controller">Critical Section Controller</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class HttpFactory extends BaseHttpFactory {

    HttpFactory(String testElementName) {
        super(testElementName, HTTPSamplerProxy, HttpTestSampleGui, false, DslDefinition.HTTP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        super.updateTestElementProperties(testElement, name, value, config)

        // Advanced configuration

        // Impl configuration
        testElement.implementation = readValue(String, config.impl, '')

        // Timeouts
        testElement.connectTimeout = readValue(String, config.connectTimeout, '')
        testElement.responseTimeout = readValue(String, config.responseTimeout, '')

        // Embedded resource
        testElement.imageParser = readValue(Boolean, config.downloadEmbeddedResources, false)
        testElement.concurrentDwn = readValue(Boolean, config.embeddedConcurrent, false)
        testElement.concurrentPool = readValue(Integer, config.embeddedConcurrentDownloads, 6)
        testElement.embeddedUrlRE = readValue(String, config.embeddedResourceUrl, '')

        // Source address
        testElement.ipSource = readValue(String, config.ipSource, '')
        String ipSourceType = readValue(String, config.ipSourceType, null)

        if (ipSourceType != null) {
            if (ipSourceType == 'hostname') {
                testElement.ipSourceType = HTTPSamplerBase.SourceType.HOSTNAME.ordinal()
            } else if (ipSourceType == 'device') {
                testElement.ipSourceType = HTTPSamplerBase.SourceType.DEVICE.ordinal()
            } else if (ipSourceType == 'deviceIp4') {
                testElement.ipSourceType = HTTPSamplerBase.SourceType.DEVICE_IPV4.ordinal()
            } else if (ipSourceType == 'deviceIp6') {
                testElement.ipSourceType = HTTPSamplerBase.SourceType.DEVICE_IPV6.ordinal()
            }
        }

        // Proxy configuration
        testElement.setProperty(HTTPSamplerBase.PROXYSCHEME, readValue(String, config.proxySchema, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYHOST, readValue(String, config.proxyHost, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYPORT, readValue(String, config.proxyPort, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYUSER, readValue(String, config.proxyUser, ''))
        testElement.setProperty(HTTPSamplerBase.PROXYPASS, readValue(String, config.proxyPassword, ''))

        // Use md5 configuration
        testElement.MD5 = readValue(config.saveAsMD5, false)
    }
}
