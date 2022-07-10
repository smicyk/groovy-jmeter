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
package net.simonix.dsl.jmeter.model

import groovy.transform.CompileStatic

/**
 * Represents check node in test tree structure.
 * <p>
 * It is container for checks.
 */
@CompileStatic
class CheckTestElementNode {

    boolean enabled
    String applyTo

    List<TestElementNode> testElementNodes = [] as LinkedList<TestElementNode>

    CheckTestElementNode(boolean enabled, String applyTo) {
        this.enabled = enabled
        this.applyTo = applyTo
    }

    void add(TestElementNode testElementNode) {
        this.testElementNodes << testElementNode
    }
}
