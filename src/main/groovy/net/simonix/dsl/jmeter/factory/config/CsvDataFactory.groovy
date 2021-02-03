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
import org.apache.jmeter.config.CSVDataSet
import org.apache.jmeter.testbeans.gui.TestBeanGUI
import org.apache.jmeter.testelement.TestElement

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
 *   file*: string
 *   encoding: string [<strong>UTF-8</strong>]
 *   variables*: string | list
 *   delimiter: string [<strong>,</strong>]
 *   shareMode: string [<strong>all</strong>, group, thread]
 * )
 * // example usage
 * start {
 *     plan {
 *         group {
 *             csv(file: 'data.csv', names: ['id', 'name'])
 *         }
 *     }
 * }
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#CSV_Data_Set_Config">CSV Data Set Config</a>
 *
 * @see TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class CsvDataFactory extends TestElementNodeFactory {

    CsvDataFactory(String testElementName) {
        super(testElementName, CSVDataSet, TestBeanGUI, false, DslDefinition.CSV_DATA)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        boolean ignoreFirstLine = config.ignoreFirstLine
        boolean allowQuotedData = config.allowQuotedData
        boolean recycle = config.recycle
        boolean stopUser = config.stopUser

        testElement.variableNames = config.variables
        testElement.filename = config.file
        testElement.fileEncoding = config.encoding
        testElement.ignoreFirstLine = ignoreFirstLine
        testElement.delimiter = config.delimiter
        testElement.quotedData = allowQuotedData
        testElement.recycle = recycle
        testElement.stopThread = stopUser
        testElement.shareMode = "shareMode.${config.shareMode}"

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
