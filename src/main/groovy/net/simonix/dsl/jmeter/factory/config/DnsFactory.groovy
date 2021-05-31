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
import org.apache.jmeter.protocol.http.control.DNSCacheManager
import org.apache.jmeter.protocol.http.gui.DNSCachePanel
import org.apache.jmeter.testelement.TestElement

/**
 * The factory class responsible for building <code>dns</code> element in the test.
 *
 * <pre>
 * // element structure
 * dns (
 *   clearEachIteration: boolean [<strong>false</strong>]
 *   useSystem: boolean          [<strong>true</strong>]
 *   servers: list
 *   values: map
 * ) {
 *     {@link DnsHostFactory host}
 * }
 * // example usage
 * start {
 *     plan {
 *         group {
 *             dns {
 *                 host 'www.example.com', address: '127.0.0.1'
 *                 host 'www.example.com': 127.0.0.1
 *             }
 *             dns servers: [ 'dns.com' ], values: [
 *              'www.example.com': '127.0.0.1'
 *             ]
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#DNS_Cache_Manager">DNS Cache Manager</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 * @see DnsHostFactory DnsHostFactory
 */
@CompileDynamic
final class DnsFactory extends TestElementNodeFactory {

    DnsFactory() {
        super(DslDefinition.DNS.title, DNSCacheManager, DNSCachePanel, false, DslDefinition.DNS)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean clearEachIteration = config.clearEachIteration
        boolean useSystem = config.useSystem

        List<String> servers = config.servers as List
        Map<String, String> values = config.values as Map

        if(config.isPresent('useSystem')) {
            if(useSystem) {
                testElement.customResolver = false
            } else {
                if(servers) {
                    testElement.customResolver = true
                } else {
                    testElement.customResolver = false
                }
            }
        } else {
            if(servers) {
                testElement.customResolver = true
            } else {
                testElement.customResolver = false
            }
        }

        testElement.clearEachIteration = clearEachIteration

        servers.each {server ->
            testElement.addServer(server)
        }

        values.each {entry ->
            testElement.addHost(entry.key, entry.value)
        }
    }
}
