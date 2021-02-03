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
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.gui.AjpSamplerGui
import org.apache.jmeter.protocol.http.sampler.AjpSampler
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>ajp</code> element in the test.
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
 *     // Embedded resource
 *     downloadEmbeddedResources: boolean [<strong>false</strong>]
 *     embeddedConcurrent: boolean [<strong>false</strong>]
 *     embeddedConcurrentDownloads: integer [<strong>6</strong>]
 *     embeddedResourceUrl: string
 *     // Use md5 configuration
 *     saveAsMD5: boolean [<strong>false</strong>]
 * ) {
 *     body | params | headers
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see BaseHttpFactory BaseHttpFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class AjpFactory extends BaseHttpFactory {

    AjpFactory(String testElementName) {
        super(testElementName, AjpSampler, AjpSamplerGui, false, DslDefinition.AJP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        super.updateTestElementProperties(testElement, name, value, config)

        // Embedded resource
        testElement.imageParser = config.downloadEmbeddedResources
        testElement.concurrentDwn = config.embeddedConcurrent
        testElement.concurrentPool = config.embeddedConcurrentDownloads
        testElement.embeddedUrlRE = config.embeddedResourceUrl

        // Use md5 configuration
        testElement.MD5 = config.saveAsMD5
    }
}
