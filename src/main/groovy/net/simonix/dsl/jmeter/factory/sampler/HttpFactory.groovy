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
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerFactory
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.testelement.TestElement

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
final class HttpFactory extends BaseHttpFactory {

    HttpFactory() {
        super(HTTPSamplerProxy, HttpTestSampleGui, DslDefinition.HTTP)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        super.updateTestElementProperties(testElement, name, value, config)

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
}
