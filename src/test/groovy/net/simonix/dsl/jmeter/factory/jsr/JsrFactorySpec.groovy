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
package net.simonix.dsl.jmeter.factory.jsr

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class JsrFactorySpec extends TempFileSpec {
    def "Check jsr generation"() {
        expect: "configure test and save result to the file"
        def config = configure {
            plan {
                group {
                    jsrsampler('inline': 'vars.put("var_groovy", "123")', cacheKey: false, file: 'test.groovy', language: 'groovy') {
                        jsrassertion('inline': 'vars.get("var_groovy") == "123"', cacheKey: false, file: 'test.groovy', language: 'groovy')
                    }
                    jsrtimer('inline': '10', cacheKey: false, file: 'test.groovy', language: 'groovy')

                    jsrsampler('inline': 'vars.put("var_groovy", "123")', cacheKey: false, file: 'test.groovy', language: 'groovy') {
                        jsrpostprocessor('inline': 'true', cacheKey: false, file: 'test.groovy', language: 'groovy')
                        jsrpreprocessor('inline': 'false', cacheKey: false, file: 'test.groovy', language: 'groovy')
                    }
                    jsrlistener('inline': 'true', cacheKey: false, file: 'test.groovy', language: 'groovy')
                }
            }
        }

        File resultFile = tempFolder.newFile('jsr_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('jsr_0.jmx', resultFile)
    }
}
