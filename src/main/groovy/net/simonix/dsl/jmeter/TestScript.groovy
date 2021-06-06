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
package net.simonix.dsl.jmeter

import groovy.cli.picocli.CliBuilder
import groovy.cli.picocli.OptionAccessor
import groovy.transform.CompileDynamic

/**
 * Test script implementation with command line support.
 * <p>
 * All standalone script should use this implementation.
 *
 * <pre>
 * // example of script with command line support
 * {@literal @}GrabConfig (systemClassLoader=true)
 * {@literal @}Grab('net.simonix.scripts:groovy-jmeter')
 *
 * {@literal @}groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script
 *
 * start {
 *   plan {
 *     group {
 *     }
 *   }
 * }
 * </pre>
 */
@CompileDynamic
abstract class TestScript extends TestScriptBase {

    abstract Object executeScript()

    Object run() {
        CliBuilder cliBuilder = new CliBuilder()

        cliBuilder.with {
            h(longOpt: 'help', args: 0, 'Show help message')
            V(longOpt: 'vars', type: Map, valueSeparator: '=', argName: 'variable=value', 'Define values for placeholders in the script')
            _(longOpt: 'jmx-out', args: 1, argName: 'file', 'Filename of .jmx file generated from this script')
            _(longOpt: 'no-run', args: 0, 'Execute script but don\'t run tests')
        }

        OptionAccessor options = cliBuilder.parse(args)

        if (!options) {
            System.err << 'Error while parsing command-line options.\n'
            System.exit(1)
        }

        if (options.h) {
            cliBuilder.usage()

            System.exit(0)
        }

        Map<String, Object> script = [:]
        script.options_enabled = true
        if (options.'jmx-out') {
            script.jmx_output = options.'jmx-out'
        } else {
            script.jmx_output = null
        }

        script.no_run = options.'no-run'
        script.variables = [:]

        if (options.Vs) {
            options.Vs.each { String name, String value ->
                script.variables[name] = value
            }
        }

        binding.setProperty('script', script)

        updateJMeterClassPath()

        // here we execute normal script code
        executeScript()
    }
}
