package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ProxyTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement

import java.util.regex.Matcher

@CompileDynamic
class HttpProxyFactory extends TestElementFactory {

    static final URL_HOSTNAME_WITHOUT_PORT = /^(?<scheme>)(?<host>[a-zA-Z0-9]+[-a-zA-Z0-9.]*)(?<port>)$/
    static final URL_PROTOCOL_WITHOUT_PORT = /^(?<scheme>https?):\/\/(?<host>[a-zA-Z0-9]+[-a-zA-Z0-9.]*)(?<port>)$/
    static final URL_PROTOCOL = /^(?<scheme>https?):\/\/(?<host>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)$/
    static final URL_HOSTNAME = /^(?<scheme>)(?<host>[a-zA-Z0-9]+[-a-zA-Z0-9.]*):(?<port>[0-9]+)$/

    static final NAME_PATTERNS = [ URL_HOSTNAME, URL_PROTOCOL, URL_PROTOCOL_WITHOUT_PORT, URL_HOSTNAME_WITHOUT_PORT]

    HttpProxyFactory() {
        super(ProxyTestElement, DslDefinition.HTTP_PROXY)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String scheme = config.scheme
        String host = config.host
        String port = config.port
        String username = config.username
        String password = config.password

        // override config elements
        if (value != null) {
            Matcher matches = NAME_PATTERNS.collect { value =~ it }.find { it.find() }

            if (matches != null) {
                scheme = matches.group('scheme') ?: scheme
                host = matches.group('host') ?: host
                if (matches.group('port')) {
                    port = matches.group('port')
                }
            }
        }

        testElement.scheme = scheme
        testElement.host = host
        testElement.port = port
        testElement.username = username
        testElement.password = password
    }

    void updateOnComplete(Object parent, Object child) {
        if (parent instanceof HTTPSamplerBase && child instanceof ProxyTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            sampler.proxyScheme = child.scheme
            sampler.proxyHost = child.host
            sampler.proxyPortInt = child.port
            sampler.proxyUser = child.username
            sampler.proxyPass = child.password
        }
    }
}
