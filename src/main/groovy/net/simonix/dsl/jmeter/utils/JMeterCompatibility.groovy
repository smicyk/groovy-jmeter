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
package net.simonix.dsl.jmeter.utils

import org.apache.jmeter.JMeter
import org.apache.jmeter.control.ReplaceableController
import org.apache.jmeter.gui.tree.JMeterTreeModel
import org.apache.jmeter.gui.tree.JMeterTreeNode
import org.apache.jorphan.collections.HashTree
import org.apache.jorphan.collections.SearchByClass


/**
 * The following class has parts of {@link JMeter#runNonGui(java.lang.String, java.lang.String, boolean, java.lang.String, boolean)} original code to maintain compatibility during not-gui test run.
 */
class JMeterCompatibility {

    /**
     * Ensure test tree is properly build before running test.
     *
     * The method contains original {@link JMeter#runNonGui(java.lang.String, java.lang.String, boolean, java.lang.String, boolean)} code.
     * @param tree
     * @return compatible tree
     */
    static HashTree ensureCompatibilty(HashTree tree) {
        @SuppressWarnings("deprecation") // Deliberate use of deprecated ctor
        JMeterTreeModel treeModel = new JMeterTreeModel(new Object())// NOSONAR Create non-GUI version to avoid headless problems
        JMeterTreeNode root = (JMeterTreeNode) treeModel.getRoot()
        treeModel.addSubTree(tree, root)

        // Hack to resolve ModuleControllers in non GUI mode
        SearchByClass<ReplaceableController> replaceableControllers =
                new SearchByClass<>(ReplaceableController.class);
        tree.traverse(replaceableControllers)
        Collection<ReplaceableController> replaceableControllersRes = replaceableControllers.getSearchResults();
        for (ReplaceableController replaceableController : replaceableControllersRes) {
            replaceableController.resolveReplacementSubTree(root)
        }

        return JMeter.convertSubTree(tree, true)
    }
}
