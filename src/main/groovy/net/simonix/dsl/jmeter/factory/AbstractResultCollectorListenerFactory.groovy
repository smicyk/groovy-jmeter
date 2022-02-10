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
package net.simonix.dsl.jmeter.factory

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jmeter.reporters.Summariser
import org.apache.jmeter.samplers.SampleSaveConfiguration
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Base class for all @{link ResultCollector} based test elements.
 */
@CompileDynamic
abstract class AbstractResultCollectorListenerFactory extends TestElementNodeFactory {

    AbstractResultCollectorListenerFactory(Class testElementGuiClass, KeywordDefinition definition) {
        super(ResultCollector, testElementGuiClass, definition)
    }

    TestElement newTestElement(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Summariser summariser = new Summariser()

        return new ResultCollector(summariser)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        testElement.filename = readValue(value, config.file)
        testElement.errorLogging = config.errorsOnly
        testElement.successOnlyLogging = config.successesOnly

        SampleSaveConfiguration saveConfig = testElement.saveConfig

        saveConfig.with {
            assertions = readValue(config.assertions, saveConfig.assertions)
            bytes = readValue(config.bytes, saveConfig.bytes)
            code = readValue(config.code, saveConfig.code)
            connectTime = readValue(config.connectTime, saveConfig.connectTime)
            dataType = readValue(config.dataType, saveConfig.dataType)
            encoding = readValue(config.encoding, saveConfig.encoding)
            fieldNames = readValue(config.fieldNames, saveConfig.fieldNames)
            fileName = readValue(config.fileName, saveConfig.fileName)
            hostname = readValue(config.hostname, saveConfig.hostname)
            idleTime = readValue(config.idleTime, saveConfig.idleTime)
            label = readValue(config.label, saveConfig.label)
            latency = readValue(config.latency, saveConfig.latency)
            message = readValue(config.message, saveConfig.message)
            //printMilliseconds = readValue(config.printMilliseconds, saveConfig.printMilliseconds)
            requestHeaders = readValue(config.requestHeaders, saveConfig.requestHeaders)
            responseData = readValue(config.responseData, saveConfig.responseData)
            //responseDataOnError = readValue(config.responseDataOnError, saveConfig.responseDataOnError)
            responseHeaders = readValue(config.responseHeaders, saveConfig.responseHeaders)
            sampleCount = readValue(config.sampleCount, saveConfig.sampleCount)
            samplerData = readValue(config.samplerData, saveConfig.samplerData)
            //saveAssertionResultsFailureMessage = readValue(config.saveAssertionResultsFailureMessage, saveConfig.saveAssertionResultsFailureMessage)
            sentBytes = readValue(config.sentBytes, saveConfig.sentBytes)
            subresults = readValue(config.subresults, saveConfig.subresults)
            success = readValue(config.success, saveConfig.success)
            threadCounts = readValue(config.threadCounts, saveConfig.threadCounts)
            threadName = readValue(config.threadName, saveConfig.threadName)
            time = readValue(config.time, saveConfig.time)
            timestamp = readValue(config.timestamp, saveConfig.timestamp)
            url = readValue(config.url, saveConfig.url)
            asXml = readValue(config.xml, saveConfig.xml)
        }
    }
}
