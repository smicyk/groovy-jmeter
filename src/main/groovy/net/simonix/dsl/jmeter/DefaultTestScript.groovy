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

import groovy.transform.CompileDynamic

/**
 * Default implementation of script builder. It should be used when scripts are used inside application.
 * <p>
 * This implementation doesn't have command line support.
 *
 * @see TestScript
 */
@CompileDynamic
abstract class DefaultTestScript extends TestScriptBase {

    abstract Object executeScript()

    Object run() {
        Map<String, Object> script = [:]
        script.options_enabled = true

        binding.setProperty('script', script)

        updateJMeterClassPath()

        // here we execute normal script code
        return executeScript()
    }
}
