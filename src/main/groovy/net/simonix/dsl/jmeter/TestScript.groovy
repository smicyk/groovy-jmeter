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
            w(longOpt: 'worker', args: 0, argName: 'worker', 'Starts script in worker mode (will wait for controller to execute test)')
            c(longOpt: 'controller', args: 0, argName: 'controller', 'Starts script in controller mode (execute test on remote workers)')
            r(longOpt: 'remote-worker', args: 1, argName: 'hostname:port', 'Remote work address (hostname:port)')
            _(longOpt: 'jmx-out', args: 1, argName: 'file', 'Filename of .jmx file generated from this script')
            _(longOpt: 'no-run', args: 0, 'Execute script but don\'t run tests')
            _(longOpt: 'worker-port', args: 1, argName: 'port', 'Worker node port (default 1099)')
            _(longOpt: 'worker-hostname', args: 1, argName: 'hostname', 'Worker node hostname')
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

        if(options.w && options.c) {
            System.err << 'Script can\'t work as worker and controller in the same time'

            System.exit(1)
        }

        if(options.w) {
            script.worker = true

            if(options.'worker-port') {
                script.worker_port = options.'worker-port'
            } else {
                script.worker_port = '1099'
            }

            if(options.'worker-hostname') {
                script.worker_hostname = options.'worker-hostname'
            } else {
                System.err << 'Worker hostname can\'t be empty'

                System.exit(1)
            }
        }

        if(options.c) {
            script.controller = true

            if(options.rs) {
                script.remote_workers = options.rs as List<String>
            } else {
                System.err << 'Remote workers can\'t be empty'

                System.exit(1)
            }
        }

        binding.setProperty('script', script)

        updateJMeterClassPath()

        // here we execute normal script code
        return executeScript()
    }
}
