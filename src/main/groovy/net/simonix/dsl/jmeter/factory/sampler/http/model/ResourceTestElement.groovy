package net.simonix.dsl.jmeter.factory.sampler.http.model

import org.apache.jmeter.testelement.AbstractTestElement
import org.apache.jmeter.testelement.TestElement

class ResourceTestElement extends AbstractTestElement implements TestElement {
    Boolean downloadParallel
    Integer parallel
    String urlInclude
    String urlExclude
}
