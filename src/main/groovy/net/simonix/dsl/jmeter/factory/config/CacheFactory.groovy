/*
 * Copyright 2019 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.config

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.control.CacheManager
import org.apache.jmeter.protocol.http.gui.CacheManagerGui
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * The factory class responsible for building <code>cache</code> element in the test.
 *
 * <pre>
 * // element structure
 * cache (
 *   clearEachIteration: boolean [<strong>false</strong>]
 *   useExpires: boolean [<strong>true</strong>]
 *   maxSize: integer [<strong>5000</strong>]
 * )
 * // example usage
 * start {
 *   plan {
 *     cache(clearEachIteration: true, useExpires: false, maxSize: 1000)
 *   }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Cache_Manager">HTTP Cache Manager</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class CacheFactory extends TestElementNodeFactory {

    CacheFactory() {
        super(DslDefinition.CACHE.title, CacheManager, CacheManagerGui, true, DslDefinition.CACHE)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean clearEachIteration = config.clearEachIteration
        boolean useExpires = config.useExpires

        testElement.clearEachIteration = clearEachIteration
        testElement.useExpires = useExpires
        testElement.maxSize = config.maxSize
    }
}
