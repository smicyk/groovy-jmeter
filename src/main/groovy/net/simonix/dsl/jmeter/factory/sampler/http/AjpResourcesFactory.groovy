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
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase

/**
 * The factory class responsible for building <code>resources</code> element inside ajp element.
 *
 * <pre>
 * // element structure
 * resources (
 *     // Embedded resource
 *     parallel: int [<strong>6</strong>]
 *     urlInclude: string
 *     urlExclude: string
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request">HTTP Sampler</a>
 *
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class AjpResourcesFactory extends AbstractResourcesFactory {

    AjpResourcesFactory() {
        super(DslDefinition.AJP_RESOURCES)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
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
