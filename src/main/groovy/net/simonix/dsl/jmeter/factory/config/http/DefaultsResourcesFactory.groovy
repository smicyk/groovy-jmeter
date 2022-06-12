/*
 * Copyright 2022 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.config.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.sampler.http.AbstractResourcesFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSampler

/**
 * The factory class responsible for building <code>resources</code> element inside <strong>defaults</strong> element.
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
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>
 *
 * @see net.simonix.dsl.jmeter.factory.config.DefaultsFactory DefaultsFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DefaultsResourcesFactory extends AbstractResourcesFactory {

    DefaultsResourcesFactory() {
        super(DslDefinition.DEFAULTS_RESOURCES)
    }

    void updateParentProperties(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof ConfigTestElement && child instanceof ResourceTestElement) {
            ConfigTestElement testElement = parent as ConfigTestElement

            testElement.setProperty(HTTPSampler.IMAGE_PARSER, true)
            testElement.setProperty(HTTPSampler.CONCURRENT_DWN, child.downloadParallel)
            testElement.setProperty(HTTPSampler.CONCURRENT_POOL, child.parallel)

            if (child.urlInclude != null && !child.urlInclude.isEmpty()) {
                testElement.setProperty(HTTPSampler.EMBEDDED_URL_RE, child.urlInclude)
            }

            if (child.urlExclude != null && !child.urlExclude.isEmpty()) {
                testElement.setProperty(HTTPSampler.EMBEDDED_URL_EXCLUDE_RE, child.urlExclude)
            }
        }
    }
}
