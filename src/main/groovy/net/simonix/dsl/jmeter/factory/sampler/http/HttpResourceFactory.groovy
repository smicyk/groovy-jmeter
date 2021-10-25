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
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSampler
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>resources</code> element inside http element.
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
 * ) {
 *      body | params | headers  resources | proxy | timeout | source
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
class HttpResourceFactory extends TestElementFactory {

    HttpResourceFactory() {
        super(ResourceTestElement, DslDefinition.HTTP_RESOURCES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean downloadParallel = config.isPresent('parallel')

        testElement.downloadParallel = downloadParallel && config.parallel != 0
        testElement.parallel = config.parallel
        testElement.urlInclude = config.urlInclude
        testElement.urlExclude = config.urlExclude
    }

    void updateOnComplete(Object parent, Object child) {
        if ((parent instanceof HTTPSamplerBase || parent instanceof ConfigTestElement) && child instanceof ResourceTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            if(parent instanceof HTTPSamplerBase) {
                sampler.imageParser = true
                sampler.concurrentDwn = child.downloadParallel
                sampler.concurrentPool = child.parallel as String
                sampler.embeddedUrlRE = child.urlInclude
                sampler.embeddedUrlExcludeRE = child.urlExclude
            } else if(parent instanceof  ConfigTestElement) {
                testElement.setProperty(HTTPSampler.IMAGE_PARSER, true)
                testElement.setProperty(HTTPSampler.CONCURRENT_DWN, child.downloadParallel)
                testElement.setProperty(HTTPSampler.CONCURRENT_POOL, child.urlInclude)
                testElement.setProperty(HTTPSampler.EMBEDDED_URL_RE, child.urlExclude)

                if (child.urlExclude != null && !child.urlExclude.isEmpty()) {
                    testElement.setProperty(HTTPSampler.EMBEDDED_URL_EXCLUDE_RE, child.urlExclude)
                }
            }
        }
    }
}
