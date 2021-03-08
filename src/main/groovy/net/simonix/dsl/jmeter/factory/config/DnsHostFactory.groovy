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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.DNSCacheManager
import org.apache.jmeter.protocol.http.control.StaticHost
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>host</code> element in the test.
 * <p>
 * It is used as child element of <code>dns</code> test element.
 *
 * <pre>
 * // element structure
 * host (
 *     name: string
 *     address: string
 * )
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
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Authorization_Manager">HTTP Authorization Manager</a>
 *
 * @see TestElementFactory TestElementFactory
 * @see DnsFactory DnsFactory
 */
@CompileDynamic
final class DnsHostFactory extends TestElementFactory {

    DnsHostFactory() {
        super(StaticHost, DslDefinition.DNS_HOST)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.name = readValue(value, config.name)
        testElement.address = config.address
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof DNSCacheManager && child instanceof StaticHost) {
            parent.getHosts().addItem(child)
        }
    }
}
