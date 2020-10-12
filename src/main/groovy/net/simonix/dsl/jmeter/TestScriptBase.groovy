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
package net.simonix.dsl.jmeter

import groovy.transform.CompileDynamic
import org.apache.jorphan.collections.HashTree

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern

@CompileDynamic
abstract class TestScriptBase extends Script {

    final static Pattern JMETER_JARPATHS_MATCHER = ~/.*\/ApacheJMeter_[A-Za-z-.0-9]+\.jar$/

    final static String JMETER_PROPERITES_FILE = 'jmeter.properties'
    final static String JMETER_SAVESERVICE_FILE = 'saveservice.properties'

    final static String JMETER_PROPERTIES_CLASSPATH = 'org/apache/jmeter/jmeter.properties'
    final static String JMETER_SAVESERVICE_CLASSPATH = 'org/apache/jmeter/saveservice.properties'

    void start(Closure c) {
        start([:], c)
    }

    void start(Map config, Closure c) {
        updateJMeterPropertiesLocation(config)

        Map script = binding.getProperty('script') as Map

        boolean optionsEnabled = script.options_enabled

        String saveTo = config.saveTo
        boolean noRun = false

        if (optionsEnabled) {
            saveTo = script.jmx_output
            noRun = script.no_run
        }

        String saveToPath = saveTo != null ? saveTo : null

        HashTree configuration = TestScriptRunner.configure(config, c)

        if (saveToPath) {
            TestScriptRunner.save(configuration, saveToPath)
        }

        if (!noRun) {
            TestScriptRunner.run(configuration)
        }
    }

    protected void updateJMeterClassPath() {
        // since scripts could be run with Grape, the dependencies don't exist on standard classpath
        // we must add apache jmeter jars so all functions and others classes are visible
        List<String> jmeterJars = System.getProperty('java.class.path')
                .tokenize(File.pathSeparator)
                .collect { it =~ JMETER_JARPATHS_MATCHER }
                .findAll { it.find() }

        // if everything is on the classpath don't change it
        if (!jmeterJars) {
            List<URL> urls = this.class.classLoader.URLs

            // if not we must find all loaded jmeter jars by Grape and add them to the classpath
            String classPathPostfix = urls.collect { it.file =~ JMETER_JARPATHS_MATCHER }
                    .findAll { it.find() }
                    .collect { it.group() }
                    .join(File.pathSeparator)

            String classPath = System.getProperty('java.class.path')

            System.setProperty('java.class.path', classPath + File.pathSeparator + classPathPostfix)
        }
    }

    protected void updateJMeterPropertiesLocation(Map config) {
        // check if we have setup path to jmeter.properties file and file exists
        String path = config.path

        if (!path) {
            // the jmeter.properties file is not provided, first check if it is in current directory
            Path jmeterPropertiesPath = FileSystems.default.getPath(JMETER_PROPERITES_FILE)

            if (Files.notExists(jmeterPropertiesPath)) {
                // if not exists, create the default one from the class path
                this.class.classLoader.getResourceAsStream(JMETER_PROPERTIES_CLASSPATH).with { sourceStream ->
                    Files.copy(sourceStream, jmeterPropertiesPath)
                }
            }

            // set file so it can be picked up by jmeter engine
            config.path = JMETER_PROPERITES_FILE
        }

        // let's load the saveservice.properties file
        Path savePropertiesPath = FileSystems.default.getPath(JMETER_SAVESERVICE_FILE)

        if (Files.notExists(savePropertiesPath)) {
            // if not exists, create the default one from the class path
            this.class.classLoader.getResourceAsStream(JMETER_SAVESERVICE_CLASSPATH).with { sourceStream ->
                Files.copy(sourceStream, savePropertiesPath)
            }
        }

        config.saveServiceProperties = JMETER_SAVESERVICE_FILE
    }
}
