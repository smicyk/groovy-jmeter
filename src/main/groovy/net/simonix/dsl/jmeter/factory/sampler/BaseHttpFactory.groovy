/*
 * Copyright 2020 Szymon Micyk
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
import org.apache.jmeter.protocol.http.util.HTTPConstants
import org.apache.jmeter.testelement.TestElement

import java.util.regex.Matcher

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Base class for HTTP related factories.
 *
 * @see AjpFactory* @see HttpFactory
 */
@CompileDynamic
abstract class BaseHttpFactory extends TestElementNodeFactory {

    static final URL_METHODS = [
            HTTPConstants.GET,
            HTTPConstants.POST,
            HTTPConstants.HEAD,
            HTTPConstants.PUT,
            HTTPConstants.OPTIONS,
            HTTPConstants.TRACE,
            HTTPConstants.DELETE,
            HTTPConstants.PATCH,
            HTTPConstants.PROPFIND,
            HTTPConstants.PROPPATCH,
            HTTPConstants.MKCOL,
            HTTPConstants.COPY,
            HTTPConstants.MOVE,
            HTTPConstants.LOCK,
            HTTPConstants.UNLOCK,
            HTTPConstants.REPORT,
            HTTPConstants.MKCALENDAR,
            HTTPConstants.SEARCH,
    ]

    static final URL_PROTOCOL = /(?<method>${URL_METHODS.join('|')}) +(?<protocol>https?):\/\/(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/
    static final URL_HOSTNAME = /(?<method>${URL_METHODS.join('|')}) +(?<protocol>)(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/
    static final URL_PORT = /(?<method>${URL_METHODS.join('|')}) +(?<protocol>)(?<domain>):?(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/
    static final URL_PATH = /(?<method>${URL_METHODS.join('|')}) +(?<protocol>)(?<domain>)(?<port>)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/

    static final NAME_PATTERNS = [ URL_PATH, URL_PORT, URL_HOSTNAME, URL_PROTOCOL ]

    protected BaseHttpFactory(String testElementName, Class testElementClass, Class testElementGuiClass, boolean leaf, Set<String> properties) {
        super(testElementName, testElementClass, testElementGuiClass, leaf, DslDefinition.HTTP_COMMON_PROPERTIES + properties)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String method = readValue(config.method, '')
        String protocol = readValue(config.protocol, '')
        String domain = readValue(config.domain, '')
        String path = readValue(config.path, '')

        Integer port = null
        if (config.containsKey('port')) {
            port = readValue(Integer, config.port, null)
        }

        // override config elements
        if (value != null) {
            Matcher matches = NAME_PATTERNS.collect { value =~ it }.find { it.find() }

            if (matches != null) {
                method = matches.group('method') ?: method
                protocol = matches.group('protocol') ?: protocol
                domain = matches.group('domain') ?: domain
                if (matches.group('port')) {
                    port = matches.group('port').toInteger()
                }
                path = matches.group('path') ?: path

                testElement.name = evaluateElementName(method, protocol, domain, port, path)
            }
        }

        // Web Server configuration
        testElement.protocol = protocol
        testElement.domain = domain

        if (port != null) {
            testElement.port = port
        }

        // Request configuration
        testElement.method = method
        testElement.path = path
        testElement.contentEncoding = readValue(config.encoding, '')
        testElement.autoRedirects = readValue(config.autoRedirects, false)
        testElement.followRedirects = readValue(config.followRedirects, true)
        testElement.useKeepAlive = readValue(config.keepAlive, true)
        testElement.doMultipartPost = readValue(config.multipart, false)
        testElement.doBrowserCompatibleMultipart = readValue(config.browserCompatibleMultipart, false)
    }

    private String evaluateElementName(String method, String protocol, String domain, Integer port, String path) {
        String elementName = "${method} ${path}"

        return elementName.replaceAll(/(\$\{(.*?)\})/) { input, expression, variable -> ":$variable" }
    }
}
