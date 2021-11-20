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
package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.sampler.http.model.SourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase

/**
 * The factory class responsible for building <code>source</code> element inside http element.
 *
 * <pre>
 * // element structure
 * source (
 *     // Source address
 *     address: string
 *     type: string [hostname, device, deviceIp4, deviceIp6]
 * ) {
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see net.simonix.dsl.jmeter.factory.sampler.HttpFactory HttpFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class HttpSourceFactory extends AbstractSourceFactory {

    HttpSourceFactory() {
        super(DslDefinition.HTTP_SOURCE)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof HTTPSamplerBase && child instanceof SourceTestElement) {
            HTTPSamplerBase sampler = parent as HTTPSamplerBase

            sampler.ipSource = child.address
            sampler.ipSourceType = child.typeValue
        }
    }
}
