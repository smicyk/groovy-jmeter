package net.simonix.dsl.jmeter.factory.controller.raw

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.testelement.TestElement

import static net.simonix.dsl.jmeter.utils.ConfigUtils.loadFromFile
import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

@CompileDynamic
class IncludeRawFactory extends TestElementNodeFactory {

    IncludeRawFactory() {
        super(IncludeRawTestElement, IncludeRawTestElement, DslDefinition.INCLUDE_RAW)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String content = readValue(value, config.inline)

        String file = config.file
        if (file != null) {
            content = loadFromFile(file, 'UTF-8')
        }

        testElement.content = content
    }
}
