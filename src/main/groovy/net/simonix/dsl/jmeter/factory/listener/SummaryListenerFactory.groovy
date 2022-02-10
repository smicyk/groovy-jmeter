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
package net.simonix.dsl.jmeter.factory.listener

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.AbstractResultCollectorListenerFactory
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.visualizers.SummaryReport

/**
 * The factory class responsible for building <code>summary</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * summary (
 *   enabled: boolean        [<strong>false</strong>]
 *   file: string
 *   errorsOnly: boolean     [<strong>false</strong>]
 *   successesOnly: boolean  [<strong>false</strong>]
 *   assertions: boolean
 *   bytes: boolean
 *   code: boolean
 *   connectTime: boolean
 *   dataType: boolean
 *   encoding: boolean
 *   fieldNames: boolean
 *   fileName: boolean
 *   hostname: boolean
 *   idleTime: boolean
 *   label: boolean
 *   latency: boolean
 *   message: boolean
 *   requestHeaders: boolean
 *   responseData: boolean
 *   responseHeaders: boolean
 *   sampleCount: boolean
 *   samplerData: boolean
 *   sentBytes: boolean
 *   subresults: boolean
 *   success: boolean
 *   threadCounts: boolean
 *   threadName: boolean
 *   time: boolean
 *   timestamp: boolean
 *   url: boolean
 *   xml: boolean
 * ) {
 * }
 *
 * // example usage
 * start {
 *     plan {
 *          summary file: 'summary.jtl', requestHeaders: true, xml: true
 *     }
 * }
 * </pre>
 *
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#Summary_Report">Summary Report</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class SummaryListenerFactory extends AbstractResultCollectorListenerFactory {

    SummaryListenerFactory() {
        super(SummaryReport, DslDefinition.SUMMARY)
    }
}
