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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class CsvDataFactorySpec extends TempFileSpec {

    def "Check csv generation"() {
        given: 'Test plan with csv config element'
        def config = configure {
            plan {
                csv(name: 'Factory csv', comments: 'Factory Comments', enabled: false, ignoreFirstLine: true, allowQuotedData: true, recycle: false, stopUser: true, file: 'test.csv', encoding: 'UTF-16', variables: ['header1', 'header2'], delimiter: ';', shareMode: 'all')
                csv(file: 'test.csv', variables: ['header'])
                csv(shareMode: 'all', file: 'test.csv', variables: ['header'])
                csv(shareMode: 'group', file: 'test.csv', variables: ['header'])
                csv(shareMode: 'thread', file: 'test.csv', variables: ['header'])
            }
        }

        File resultFile = tempFolder.newFile('csv_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('csv_0.jmx', resultFile)
    }
}
