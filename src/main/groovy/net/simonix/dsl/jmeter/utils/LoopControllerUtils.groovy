package net.simonix.dsl.jmeter.utils

import groovy.transform.CompileDynamic
import org.apache.jmeter.testelement.TestElement

@CompileDynamic
final class LoopControllerUtils {

    static void updateLoopConfig(TestElement testElement, Object count, boolean hasForever, boolean forever) {
        if (hasForever) {
            if (forever) {
                testElement.loops = -1
            } else {
                testElement.loops = count
            }
        } else {
            testElement.loops = count
        }
    }
}
