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

class CacheFactorySpec extends TempFileSpec {
    def "Check file generation with cache keyword"() {
        given: 'Test plan with cache config element'
        def config = configure {
            plan {
                cache()
                cache(name: 'Factory cache', comments: 'Factory Comments', enabled: false, clearEachIteration: true, useExpires: false, maxSize: 1000, useUserConfig: true)
            }
        }

        File resultFile = tempFolder.newFile('cache_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('cache_0.jmx', resultFile)
    }
}