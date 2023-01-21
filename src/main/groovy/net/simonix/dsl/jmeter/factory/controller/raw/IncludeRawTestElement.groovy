package net.simonix.dsl.jmeter.factory.controller.raw;

import groovy.transform.CompileDynamic;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;

@CompileDynamic
final class IncludeRawTestElement extends AbstractTestElement implements TestElement {

    String content

}
