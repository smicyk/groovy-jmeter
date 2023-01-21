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
package net.simonix.dsl.jmeter.builder

import groovy.transform.CompileStatic
import net.simonix.dsl.jmeter.factory.controller.raw.IncludeRawTestElement
import net.simonix.dsl.jmeter.model.TestElementNode
import org.apache.jmeter.save.SaveService
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.util.JMeterUtils
import org.apache.jorphan.collections.HashTree
import org.apache.jorphan.collections.ListedHashTree

/**
 * Builds {@link HashTree} structure from {@link TestElementNode} tree.
 */
@CompileStatic
final class TestTreeBuilder {

    static HashTree build(TestElementNode rootTestElementNode) {
        HashTree root = new ListedHashTree()

        updateTree(root.add(extractElement(rootTestElementNode.testElement)), rootTestElementNode)

        return root
    }

    static void updateTree(HashTree parent, TestElementNode parentTestElementNode) {
        parentTestElementNode.testElementNodes.each { testElementNode ->
            HashTree childHashTree = buildChild(parent, testElementNode.testElement)

            updateTree(childHashTree, testElementNode)
        }
    }

    static HashTree buildChild(HashTree parent, TestElement childTestElement) {
        return parent.add(extractElement(childTestElement))
    }


    static Object extractElement(TestElement testElement) {
        if(testElement instanceof IncludeRawTestElement) {
            String version = SaveService.getVERSION()
            String propertiesVersion = SaveService.getPropertiesVersion()
            String jmeterVersion = JMeterUtils.getJMeterVersion()

            File script
            try {
                script = File.createTempFile("script_", ".jmx")

                script.write("""
                    <jmeterTestPlan version="$version" properties="$propertiesVersion" jmeter="$jmeterVersion">
                        <hashTree>
                        $testElement.content
                        </hashTree>
                    </jmeterTestPlan>
                """)

                HashTree hashTree = SaveService.loadTree(script)

                return hashTree.list().iterator().next()
            } finally {
                if(script != null) {
                    script.delete()
                }
            }
        }

        return testElement
    }

}
