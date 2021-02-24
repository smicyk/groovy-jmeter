package net.simonix.dsl.jmeter.factory.extractor

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.TestElementNodeFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.extractor.XPath2Extractor
import org.apache.jmeter.extractor.gui.XPath2ExtractorGui
import org.apache.jmeter.testelement.TestElement

@CompileDynamic
final class XPathExtractorFactory  extends TestElementNodeFactory {

    XPathExtractorFactory() {
        super(DslDefinition.XPATH_EXTRACTOR.title, XPath2Extractor, XPath2ExtractorGui, true, DslDefinition.XPATH_EXTRACTOR)
    }

    void updateTestElementProperties(TestElement testElement, Object name, Object value, Map config) {
        String applyTo = config.applyTo

        if (applyTo == 'all') {
            testElement.setScopeAll()
        } else if (applyTo == 'parent') {
            testElement.setScopeParent()
        } else if (applyTo == 'children') {
            testElement.setScopeChildren()
        } else if (applyTo == 'variable') {
            testElement.setScopeVariable(variableName)
        } else {
            testElement.setScopeParent()
        }

        testElement.defaultValue = config.defaultValue
        testElement.matchNumber = config.match
        testElement.refName = config.variable
        testElement.XPathQuery = config.expression
        testElement.namespaces = config.namespaces
        testElement.fragment = config.fragment
    }
}
