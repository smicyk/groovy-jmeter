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
package net.simonix.dsl.jmeter.factory.sampler.java

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.java.control.gui.JavaTestSamplerGui
import org.apache.jmeter.protocol.java.sampler.JavaSampler
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.TestElementProperty

/**
 * The factory class responsible for building <code>java_request</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * java_request (
 *   classname: string
 * ) {
 * {@link net.simonix.dsl.jmeter.factory.listener.BackendArgumentsFactory arguments}
 * }
 *
 * // example usage
 * start {
 *     plan {
 *         group {
 *             java_request classname: 'org.apache.jmeter.protocol.java.test.JavaTest' {
 *                 arguments {
 *                     argument(name: 'Sleep_Time', value: '100')
 *                     argument(name: 'Sleep_Mask', value: '0xFF')
 *                     argument(name: 'Label', value: 'Java Sample')
 *                     argument(name: 'ResponseCode', value: '400')
 *                     argument(name: 'ResponseMessage', value: 'Not OK')
 *                     argument(name: 'Status', value: 'NO_OK')
 *                     argument(name: 'SamplerData', value: '')
 *                     argument(name: 'ResultData', value: '')
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Java_Request">Java Request</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class JavaRequestFactory extends TestElementNodeFactory {

    JavaRequestFactory() {
        super(JavaSampler, JavaTestSamplerGui, DslDefinition.JAVA_REQUEST)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.classname = config.classname

        testElement.property = new TestElementProperty(JavaSampler.ARGUMENTS, new Arguments())
    }
}
