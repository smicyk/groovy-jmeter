package net.simonix.dsl.jmeter.plugins.dummy.sampler

import groovy.transform.CompileDynamic
import kg.apc.jmeter.dummy.DummyElement
import kg.apc.jmeter.samplers.DummySampler
import kg.apc.jmeter.samplers.DummySamplerGui
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import net.simonix.dsl.jmeter.model.definition.KeywordCategory
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.model.constraint.Constraints.notEmpty
import static net.simonix.dsl.jmeter.model.definition.DefinitionBuilder.keyword

@CompileDynamic
class DummySamplerFactory extends TestElementNodeFactory {

    static final KeywordDefinition DUMMY = keyword('ext_dummy', KeywordCategory.SAMPLER) {
        include(DslDefinition.COMMON_PROPERTIES)

        property(name: 'waiting', type: Boolean, required: false, defaultValue: false)
        property(name: 'successful', type: Boolean, required: false, defaultValue: false)
        property(name: 'responseCode', type: String, required: false, defaultValue: '')
        property(name: 'responseMessage', type: String, required: false, defaultValue: '')
        property(name: 'requestData', type: String, required: false, defaultValue: '')
        property(name: 'responseData', type: String, required: false, defaultValue: '')
        property(name: 'responseTime', type: String, required: false, defaultValue: '')
        property(name: 'latency', type: String, required: false, defaultValue: '')
        property(name: 'connect', type: String, required: false, defaultValue: '')
        property(name: 'url', type: String, required: false, defaultValue: '')
        property(name: 'resultClass', type: String, required: true, defaultValue: 'org.apache.jmeter.samplers.SamplerResult', contraints: notEmpty())
    }

    DummySamplerFactory() {
        super('Dummy Sampler', DummySampler, DummySamplerGui, DUMMY)
    }

    @Override
    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        DummyElement dummy = (testElement as DummySampler).dummy

        dummy.setSimulateWaiting(config.waiting)
        dummy.setSuccessful(config.successful)
        dummy.setResponseCode(config.responseCode)
        dummy.setResponseMessage(config.responseMessage)
        dummy.setRequestData(config.requestData)
        dummy.setResponseData(config.responseData)
        dummy.setResponseTime(config.responseTime)
        dummy.setLatency(config.latency)
        dummy.setConnectTime(config.connect)
        dummy.setURL(config.url)
        dummy.setResultClass(Objects.requireNonNull(config.resultClass))
    }
}
