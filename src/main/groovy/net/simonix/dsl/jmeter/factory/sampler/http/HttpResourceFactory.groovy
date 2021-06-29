package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ProxyTestElement
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.testelement.TestElement

import java.util.regex.Matcher

@CompileDynamic
class HttpResourceFactory extends TestElementFactory {

    HttpResourceFactory() {
        super(ResourceTestElement, DslDefinition.HTTP_RESOURCE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean downloadParallel = config.isPresent('parallel')

        testElement.downloadParallel = downloadParallel && config.parallel != 0
        testElement.parallel = config.parallel
        testElement.urlInclude = config.urlInclude
        testElement.urlExclude = config.urlExclude
    }

    void updateOnComplete(Object parent, Object child) {
        if (parent instanceof HTTPSamplerBase && child instanceof ResourceTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            sampler.imageParser = true
            sampler.concurrentDwn = child.downloadParallel
            sampler.concurrentPool = child.parallel as String
            sampler.embeddedUrlRE = child.urlInclude
            sampler.embeddedUrlExcludeRE = child.urlExclude
        }
    }
}
