/*
 * Copyright 2021 Szymon Micyk
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
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.config.GraphQLRequestParams
import org.apache.jmeter.protocol.http.config.gui.GraphQLUrlConfigGui
import org.apache.jmeter.protocol.http.control.gui.GraphQLHTTPSamplerGui
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerFactory
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.protocol.http.util.GraphQLRequestParamUtils
import org.apache.jmeter.protocol.http.util.HTTPArgument
import org.apache.jmeter.protocol.http.util.HTTPConstants
import org.apache.jmeter.protocol.http.util.HTTPFileArgs
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty

/**
 * The factory class responsible for building <code>resources</code> element inside http element.
 *
 * <pre>
 * // element structure
 * resources (
 *     // Embedded resource
 *     parallel: int [<strong>6</strong>]
 *     urlInclude: string
 *     urlExclude: string
 * ) {
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class GraphQLFactory extends BaseHttpFactory {

    GraphQLFactory() {
        super(HTTPSamplerProxy, GraphQLHTTPSamplerGui, DslDefinition.GRAPHQL)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        super.updateTestElementProperties(testElement, name, value, config)

        testElement.postBodyRaw = HTTPConstants.GET != testElement.method

        testElement.setProperty(GraphQLUrlConfigGui.OPERATION_NAME, config.operation as String)

        // Impl configuration
        String impl = config.impl

        if(impl != null) {
            if(impl == 'java') {
                testElement.implementation = HTTPSamplerFactory.IMPL_JAVA
            } else if(impl == 'http') {
                testElement.implementation = HTTPSamplerFactory.IMPL_HTTP_CLIENT4
            }
        }

        // Use md5 configuration
        testElement.MD5 = config.saveAsMD5
    }

    void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node) {
        if (node instanceof TestElementNode && node.testElement instanceof HTTPSamplerProxy) {
            HTTPSamplerBase element = node.testElement as HTTPSamplerProxy

            GraphQLRequestParams params = new GraphQLRequestParams(
                    element.getPropertyAsString(GraphQLUrlConfigGui.OPERATION_NAME),
                    element.getPropertyAsString(GraphQLUrlConfigGui.QUERY),
                    element.getPropertyAsString(GraphQLUrlConfigGui.VARIABLES))

            Arguments arguments = new Arguments()

            if (HTTPConstants.GET == element.method) {
                if (params.getOperationName() != null && !params.getOperationName().isEmpty()) {
                    arguments.addArgument(createHTTPArgument("operationName", params.getOperationName(), true))
                }

                arguments.addArgument(createHTTPArgument("query",
                        GraphQLRequestParamUtils.queryToGetParamValue(params.getQuery()), true))

                if (params.getVariables() != null && !params.getVariables().isEmpty()) {
                    final String variablesParamValue = GraphQLRequestParamUtils
                            .variablesToGetParamValue(params.getVariables())
                    if (variablesParamValue != null) {
                        arguments.addArgument(createHTTPArgument("variables", variablesParamValue, true))
                    }
                }
            } else {
                arguments.addArgument(createHTTPArgument("", GraphQLRequestParamUtils.toPostBodyString(params), false))
            }

            element.setProperty(new TestElementProperty(HTTPSamplerBase.ARGUMENTS, arguments))
        }
    }

    private HTTPArgument createHTTPArgument(String name, String value, boolean alwaysEncoded) {
        final HTTPArgument arg = new HTTPArgument(name, value)
        arg.setUseEquals(true)
        arg.setEnabled(true)
        arg.setAlwaysEncoded(alwaysEncoded)
        return arg
    }
}
