package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ExecuteTestElement
import net.simonix.dsl.jmeter.factory.sampler.http.model.ProxyTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.config.GraphQLRequestParams
import org.apache.jmeter.protocol.http.config.gui.GraphQLUrlConfigGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.util.GraphQLRequestParamUtils
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.protocol.http.util.HTTPConstants
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty

import static net.simonix.dsl.jmeter.utils.ConfigUtils.loadFromFile
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
class GraphQLExecuteFactory extends TestElementFactory {

    GraphQLExecuteFactory() {
        super(ExecuteTestElement, DslDefinition.GRAPHQL_EXECUTE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String content = readValue(value, config.inline)

        String file = config.file
        if (file != null) {
            content = loadFromFile(file, 'UTF-8')
        }

        testElement.content = content
    }

    void updateOnComplete(Object parent, Object child) {
        if (parent instanceof HTTPSamplerBase && child instanceof ExecuteTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            sampler.setProperty(GraphQLUrlConfigGui.QUERY, child.content)
        }
    }
}