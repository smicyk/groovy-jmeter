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
package net.simonix.dsl.jmeter.factory.extractor

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class ExtractorFactorySpec extends TempFileSpec {
    def "Check extractors generation"() {
        given: 'Test plan with extractor elements'
        def config = configure {
            plan {
                extract_css(variable: 'var_variable', expression: '#variable', attribute: 'value')
                extract_regex(variable: 'var_variable', expression: 'variable')
                extract_json(variables: 'var_variable', expressions: '$..author')
                extract_json(variables: ['var_variable1', 'var_variable2'], expressions: ['$..author', '$..book'])
                extract_xpath(variable: 'var_variable', expression: '$.', namespaces: 'ex=http://example.com')
                extract_xpath(variable: 'var_variable', expression: '$.', namespaces: ['ex=http://example.com', 'ex2=http://example.com'])
            }
        }

        File resultFile = tempFolder.newFile('extractors_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('extractors_0.jmx', resultFile)
    }

    def "Check extractors generation with all fields"() {
        given: 'Test plan with extractor elements'
        def config = configure {
            plan {
                extract_css(name: 'Extractor Factory', comments: 'Extractor Comments', enabled: false, applyTo: 'all', useEmptyValue: true, defaultValue: 'test', match: 1, variable: 'var_variable', expression: '#variable', attribute: 'value', engine: 'JODD')
                extract_regex(name: 'Extractor Factory', comments: 'Extractor Comments', enabled: false, applyTo: 'all', field: 'response_unescaped', useEmptyValue: true, defaultValue: 'test', match: 1, variable: 'var_variable', expression: 'variable', template: '\$2\$')
                extract_json(name: 'Extractor Factory', comments: 'Extractor Comments', enabled: false, applyTo: 'all', defaultValues: 'test', matches: 2, variables: 'var_variable', expressions: '$..author', concatenation: true)
                extract_xpath(name: 'Extractor Factory', comments: 'Extractor Comments', enabled: false, applyTo: 'all', defaultValue: 'test', match: 1, variable: 'var_variable', expression: '$.', namespaces: 'test', fragment: true)
            }
        }

        File resultFile = tempFolder.newFile('extractors_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('extractors_1.jmx', resultFile)
    }
}