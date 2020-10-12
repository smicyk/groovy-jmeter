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
package net.simonix.dsl.jmeter.factory.listener

import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jmeter.reporters.Summariser
import org.apache.jmeter.samplers.SampleSaveConfiguration
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.visualizers.SummaryReport

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

final class SummaryFactory extends TestElementNodeFactory {
    
    SummaryFactory(String testElementName) {
        super(testElementName, ResultCollector, SummaryReport, true)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Summariser summariser = new Summariser()

        return new ResultCollector(summariser)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.filename = readValue(config.path, '')
        testElement.errorLogging = readValue(config.errorsOnly, false)
        testElement.successOnlyLogging = readValue(config.successesOnly, false)

        SampleSaveConfiguration saveConfig = testElement.saveConfig

        saveConfig.assertions = readValue(config.assertions, saveConfig.assertions)
        saveConfig.bytes = readValue(config.bytes, saveConfig.bytes)
        saveConfig.code = readValue(config.code, saveConfig.code)
        saveConfig.connectTime = readValue(config.connectTime, saveConfig.connectTime)
        saveConfig.dataType = readValue(config.dataType, saveConfig.dataType)
        saveConfig.encoding = readValue(config.encoding, saveConfig.encoding)
        saveConfig.fieldNames = readValue(config.fieldNames, saveConfig.fieldNames)
        saveConfig.fileName = readValue(config.fileName, saveConfig.fileName)
        saveConfig.hostname = readValue(config.hostname, saveConfig.hostname)
        saveConfig.idleTime = readValue(config.idleTime, saveConfig.idleTime)
        saveConfig.label = readValue(config.label, saveConfig.label)
        saveConfig.latency = readValue(config.latency, saveConfig.latency)
        saveConfig.message = readValue(config.message, saveConfig.message)
        //saveConfig.printMilliseconds = readValue(config.printMilliseconds, saveConfig.printMilliseconds)
        saveConfig.requestHeaders = readValue(config.requestHeaders, saveConfig.requestHeaders)
        saveConfig.responseData = readValue(config.responseData, saveConfig.responseData)
        //saveConfig.responseDataOnError = readValue(config.responseDataOnError, saveConfig.responseDataOnError)
        saveConfig.responseHeaders = readValue(config.responseHeaders, saveConfig.responseHeaders)
        saveConfig.sampleCount = readValue(config.sampleCount, saveConfig.sampleCount)
        saveConfig.samplerData = readValue(config.samplerData, saveConfig.samplerData)
        //saveConfig.saveAssertionResultsFailureMessage = readValue(config.saveAssertionResultsFailureMessage, saveConfig.saveAssertionResultsFailureMessage)
        saveConfig.sentBytes = readValue(config.sentBytes, saveConfig.sentBytes)
        saveConfig.subresults = readValue(config.subresults, saveConfig.subresults)
        saveConfig.success = readValue(config.success, saveConfig.success)
        saveConfig.threadCounts = readValue(config.threadCounts, saveConfig.threadCounts)
        saveConfig.threadName = readValue(config.threadName, saveConfig.threadName)
        saveConfig.time = readValue(config.time, saveConfig.time)
        saveConfig.timestamp = readValue(config.timestamp, saveConfig.timestamp)
        saveConfig.url = readValue(config.url, saveConfig.url)
        saveConfig.asXml = readValue(config.xml, saveConfig.xml)
    }
}
