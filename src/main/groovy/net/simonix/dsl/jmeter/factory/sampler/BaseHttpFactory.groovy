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
package net.simonix.dsl.jmeter.factory.sampler

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.protocol.http.util.HTTPConstants
import org.apache.jmeter.testelement.TestElement

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Base class for HTTP related factories.
 *
 * @see AjpFactory
 * @see HttpFactory
 */
@CompileDynamic
abstract class BaseHttpFactory extends TestElementNodeFactory {

    static final String[] URL_METHODS = [
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

    protected BaseHttpFactory(Class testElementClass, Class testElementGuiClass, KeywordDefinition definition) {
        super(testElementClass, testElementGuiClass, definition)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String method = config.method
        String protocol = config.protocol
        String domain = config.domain
        String path = config.path

        Integer port = null
        if (config.containsKey('port')) {
            port = config.port as Integer
        }

        // override config elements
        if (value != null) {
            List<Pattern> namePatterns = buildUrlPatterns()
            Matcher matches = namePatterns.collect { value =~ it }.find { it.find() }

            if (matches != null) {
                method = matches.group('method') ?: method
                protocol = matches.group('protocol') ?: protocol
                domain = matches.group('domain') ?: domain
                if (matches.group('port')) {
                    port = matches.group('port').toInteger()
                }
                path = matches.group('path') ?: path

                // only if name is not provided
                if (!config.isPresent('name')) {
                    testElement.name = evaluateElementName(method, protocol, domain, port, path)
                }
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
        testElement.contentEncoding = config.encoding
        testElement.autoRedirects = config.autoRedirects
        testElement.followRedirects = config.followRedirects
        testElement.useKeepAlive = config.keepAlive
    }

    protected List<String> validMethods() {
        return URL_METHODS
    }

    protected List<Pattern> buildUrlPatterns() {
        List<Pattern> urlMethods = validMethods();

        Pattern URL_HOSTNAME_WITHOUT_PORT = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>)(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*)(?<port>)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)?/
        Pattern URL_PROTOCOL_WITHOUT_PORT = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>https?):\/\/(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*)(?<port>)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)?/
        Pattern URL_PROTOCOL = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>https?):\/\/(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)?/
        Pattern URL_HOSTNAME = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>)(?<domain>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)?/
        Pattern URL_PORT = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>)(?<domain>):(?<port>[0-9]+)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/
        Pattern URL_PATH = ~/(?<method>${urlMethods.join('|')}) +(?<protocol>)(?<domain>)(?<port>)(?<path>\/[a-zA-Z0-9\/\-_\.\u0024\{\}]+)/

        return [ URL_PATH, URL_PORT, URL_HOSTNAME, URL_PROTOCOL, URL_PROTOCOL_WITHOUT_PORT, URL_HOSTNAME_WITHOUT_PORT]
    }

    private static String evaluateElementName(String method, String protocol, String domain, Integer port, String path) {
        String elementName = "${method} ${path?:'/'}"

        return elementName.replaceAll(/(\$\{(.*?)\})/) { input, expression, variable -> ":$variable" }
    }
}
