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
import net.simonix.dsl.jmeter.model.TestElementNode
import org.apache.jmeter.testelement.TestElement
import org.apache.jorphan.collections.HashTree
import org.apache.jorphan.collections.ListedHashTree

/**
 * Builds {@link HashTree} structure from {@link TestElementNode} tree.
 */
@CompileStatic
final class TestTreeBuilder {

    static HashTree build(TestElementNode rootTestElementNode) {
        HashTree root = new ListedHashTree()

        buildTree(root.add(rootTestElementNode.testElement), rootTestElementNode)

        return root
    }

    static void buildTree(HashTree parent, TestElementNode parentTestElementNode) {
        parentTestElementNode.testElementNodes.each {
            HashTree childHashTree = buildChild(parent, it.testElement)

            buildTree(childHashTree, it)
        }
    }

    static HashTree buildChild(HashTree parent, TestElement childTestElement) {
        return parent.add(childTestElement)
    }
}
