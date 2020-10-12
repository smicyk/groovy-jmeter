/*
 * Copyright 2020 Szymon Micyk
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
package net.simonix.dsl.jmeter

/**
 * Fragment implementation of script builder. It should be used to insert scripts from file.
 *
 * <pre>
 * // example usage
 * start {
 *     plan {
 *         insert 'fragments/login.groovy'
 *     }
 * }
 * </pre>
 */
abstract class FragmentTestScript extends Script {

    abstract Object executeScript()

    Object fragment(Closure c) {
        return fragment([:], c)
    }

    Object fragment(Map config, Closure c) {
        return TestScriptRunner.invokeBuilder(config, c)
    }

    Object run() {
        // here we execute normal script code
        return executeScript()
    }
}