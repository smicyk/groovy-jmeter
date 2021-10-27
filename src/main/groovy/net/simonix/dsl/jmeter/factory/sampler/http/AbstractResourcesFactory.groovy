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
import net.simonix.dsl.jmeter.factory.TestElementFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import org.apache.jmeter.testelement.TestElement

/**
 * The abstract factory class responsible for building <code>resources</code> element inside http/ajp element.
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
abstract class AbstractResourcesFactory extends TestElementFactory {

    protected AbstractResourcesFactory(KeywordDefinition definition) {
        super(ResourceTestElement, definition)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean downloadParallel = config.isPresent('parallel')

        testElement.downloadParallel = downloadParallel && config.parallel != 0
        testElement.parallel = config.parallel
        testElement.urlInclude = config.urlInclude
        testElement.urlExclude = config.urlExclude
    }
}
