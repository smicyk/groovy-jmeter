package net.simonix.dsl.jmeter.factory.sampler.http.model

import org.apache.jmeter.testelement.AbstractTestElement
import org.apache.jmeter.testelement.TestElement

class ProxyTestElement extends AbstractTestElement implements TestElement {
    String scheme
    String host
    String port
    String username
    String password
}
