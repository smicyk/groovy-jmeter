/*
 * Copyright 2022 Szymon Micyk
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
import net.simonix.dsl.jmeter.builder.DefaultFactoryBuilder
import net.simonix.dsl.jmeter.builder.TestTreeBuilder
import net.simonix.dsl.jmeter.statistics.Statistics
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.statistics.StatisticsListener
import net.simonix.dsl.jmeter.statistics.StatisticsProvider
import org.apache.jmeter.engine.StandardJMeterEngine
import org.apache.jmeter.reporters.ResultCollector
import org.apache.jmeter.save.SaveService
import org.apache.jmeter.services.FileServer
import org.apache.jmeter.testelement.TestPlan
import org.apache.jmeter.util.JMeterUtils
import org.apache.jorphan.collections.HashTree
import org.apache.jorphan.collections.SearchByClass
import org.codehaus.groovy.runtime.InvokerHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern

@CompileDynamic
final class TestScriptRunner {

    static final Logger logger = LoggerFactory.getLogger(TestScriptRunner.class)

    final static Pattern JMETER_JARPATHS_MATCHER = ~/.*(\/|\\)ApacheJMeter_[A-Za-z-.0-9]+\.jar$/

    final static String JMETER_PROPERITES_FILE = 'jmeter.properties'
    final static String JMETER_SAVESERVICE_FILE = 'saveservice.properties'
    final static String JMETER_SAVESERVICE_PROPERTY = 'saveservice_properties'

    final static String JMETER_SAVESERVICE_CLASSPATH = 'org/apache/jmeter/saveservice.properties'

    final static String JAVA_CLASSPATH_PROPERTY = 'java.class.path'

    static HashTree configure(Closure closure) {
        return configure([:], closure)
    }

    static HashTree configure(Map config, Closure closure) {
        String jmeterPropertiesPath = config.path

        JMeterUtils.loadJMeterProperties(jmeterPropertiesPath ?: JMETER_PROPERITES_FILE)

        // if save service is provied we either run from script or user provide it in configuration
        String saveServiceProperties = config.saveServiceProperties
        if (saveServiceProperties) {
            JMeterUtils.setProperty(JMETER_SAVESERVICE_FILE, saveServiceProperties)
        } else {
            // otherwise we need to check if we run as unit test or just building it as normal application
            URL url = ClassLoader.getSystemResource(JMETER_SAVESERVICE_CLASSPATH)

            if (url.toURI().getScheme().equalsIgnoreCase('jar')) {
                Path savePropertiesPath = FileSystems.getDefault().getPath(JMETER_SAVESERVICE_FILE)

                if (Files.notExists(savePropertiesPath)) {
                    // we are running as application and needs to create saveservice.properties from default location
                    ClassLoader.getSystemResourceAsStream(JMETER_SAVESERVICE_CLASSPATH).with { sourceStream ->
                        Files.copy(sourceStream, savePropertiesPath)
                    }
                }

                JMeterUtils.setProperty(JMETER_SAVESERVICE_PROPERTY, JMETER_SAVESERVICE_FILE)
            } else {
                // we are not running from junit as a normal library build
                JMeterUtils.setProperty(JMETER_SAVESERVICE_PROPERTY, url.getFile())
            }
        }

        if (config.local_properties) {
            Properties properties = JMeterUtils.getJMeterProperties();

            config.local_properties.each { String name, Object value ->
                if(value) {
                    if(value instanceof String && value.size() > 0) {
                        properties.setProperty(name, value)
                    } else {
                        properties.remove(name)
                    }
                } else {
                    properties.remove(name)
                }
            }
        }

        JMeterUtils.initLocale()

        // we need to set jmeter libraries to search paths so functions can be picked up
        List<String> paths = System.properties[JAVA_CLASSPATH_PROPERTY].tokenize(File.pathSeparator)

        String searchPaths = paths.collect { it =~ JMETER_JARPATHS_MATCHER }
                .findAll { it.find() }
                .collect { it.group() }
                .join(';')

        JMeterUtils.setProperty('search_paths', searchPaths)

        if (config.scriptName) {
            updateFileServerBaseScript(new File(config.scriptName))
        }  else {
            logger.warn("Script name not available. Some JMeter functions might not work properly.")
        }

        return TestTreeBuilder.build(invokeBuilder(config, closure))
    }

    static TestElementNode invokeBuilder(Map config, Closure closure) {
        DefaultFactoryBuilder builder = new DefaultFactoryBuilder()

        if (config.variables) {
            config.variables.each { String name, Object value ->
                builder.setVariable(name, value)
            }
        }

        if (config.plugins) {
            config.plugins.each { factory ->
                builder.registerPluginFactory(factory)
            }
        }

        closure.delegate = builder
        closure.resolveStrategy = Closure.DELEGATE_ONLY

        return InvokerHelper.invokeClosure(closure, []) as TestElementNode
    }

    static void save(HashTree testPlan, File file) {
        updateFileServerBaseScript(file);

        SaveService.saveTree(testPlan, new FileOutputStream(file))
    }

    static void save(HashTree testPlan, String path) {
        save(testPlan, new File(path))
    }

    static StatisticsProvider run(HashTree testPlan, boolean statistics = false) {
        StandardJMeterEngine engine = new StandardJMeterEngine()


        StatisticsListener listener = statistics ? applyStatistics(testPlan) : null

        // Run Test Plan
        engine.configure(testPlan)
        engine.run()

        return listener ? listener.statistics : Statistics.empty()
    }

    static void start(Map config, Closure closure) {
        HashTree configuration = configure(config, closure)

        run(configuration, false)
    }

    static void start(Closure closure) {
        start([:], closure)
    }

    protected static void updateFileServerBaseScript(File file) {
        // set base path for script so we have access to some jmeter functions e.g. __TestPlanName
        FileServer.getFileServer().setBaseForScript(file)
    }

    private static StatisticsListener applyStatistics(HashTree hashTree) {
        SearchByClass<TestPlan> searchTestPlan = new SearchByClass<>(TestPlan)
        hashTree.traverse(searchTestPlan)
        TestPlan testPlan = searchTestPlan.getSearchResults().find()

        if (!testPlan) {
            throw new IllegalStateException('Could not find the TestPlan in the script')
        }

        StatisticsListener listener = new StatisticsListener()
        ResultCollector collector = new ResultCollector()
        collector.setListener(listener)

        HashTree testPlanTree = hashTree.getTree(testPlan)
        testPlanTree.add(collector)
        testPlanTree.add(listener)

        return listener
    }
}
