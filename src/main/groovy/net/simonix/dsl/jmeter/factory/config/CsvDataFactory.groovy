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


import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.DslDefinition
import org.apache.jmeter.config.CSVDataSet
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValues

/**
 * The factory class responsible for building <code>csv</code> element in the test.
 *
 * <pre>
 * // structure of the element
 * csv (
 *   ignoreFirstLine: boolean [<strong>false</strong>]
 *   allowQuotedData: boolean [<strong>false</strong>]
 *   recycle: boolean [<strong>true</strong>]
 *   stopUser: boolean [<strong>false</strong>]
 *   filename*: string
 *   encoding: string [<strong>UTF-8</strong>]
 *   variables*: string | list
 *   delimiter: string [<strong>,</strong>]
 *   shareMode: string [<strong>all</strong>, group, thread]
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             csv(filename: 'data.csv', names: ['id', 'name'])
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#CSV_Data_Set_Config">CSV Data Set Config</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
final class CsvDataFactory extends TestElementNodeFactory {

    CsvDataFactory(String testElementName) {
        super(testElementName, CSVDataSet, TestBeanGUI, false, DslDefinition.CSV_DATA_PROPERTIES)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean ignoreFirstLine = readValue(config.ignoreFirstLine, false)
        boolean allowQuotedData = readValue(config.allowQuotedData, false)
        boolean recycle = readValue(config.recycle, true)
        boolean stopUser = readValue(config.stopUser, false)

        testElement.variableNames = readValues(config.variables, ',', [])
        testElement.filename = readValue(config.filename, '')
        testElement.fileEncoding = readValue(config.encoding, 'UTF-8')
        testElement.ignoreFirstLine = ignoreFirstLine
        testElement.delimiter = readValue(config.delimiter, ',')
        testElement.quotedData = allowQuotedData
        testElement.recycle = recycle
        testElement.stopThread = stopUser
        testElement.shareMode = "shareMode.${readValue(config.shareMode, 'all')}"

        // we must set it separately
        testElement.setProperty('filename', testElement.filename)
        testElement.setProperty('fileEncoding', testElement.fileEncoding)
        testElement.setProperty('variableNames', testElement.variableNames)
        testElement.setProperty('ignoreFirstLine', testElement.ignoreFirstLine)
        testElement.setProperty('delimiter', testElement.delimiter)
        testElement.setProperty('quotedData', testElement.quotedData)
        testElement.setProperty('recycle', testElement.recycle)
        testElement.setProperty('stopThread', testElement.stopThread)
        testElement.setProperty('shareMode', testElement.shareMode)

        // share mode: all, group, thread
    }
}
