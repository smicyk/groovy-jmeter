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
package net.simonix.dsl.jmeter.factory.common

import net.simonix.dsl.jmeter.factory.AbstractTestElementFragmentFactory

/**
 * Helper factory responsible for <code>insert</code> test element. It is used to insert of files with test code.
 * <p>
 * The path arguments point to file in the classpath.
 *
 * <pre>
 * // element's structure
 * insert(
 *   path: string
 * )
 * // example usage
 * // main.groovy
 * start {
 *     plan {
 *         group {
 *             insert path: 'path/to/code.groovy'
 *             // or just
 *             insert 'path/to/code.groovy'
 *         }
 *     }
 * }
 * // code.groovy
 * fragment {
 *     controllers | groups | params | variables | arguments // only one element can be here but with any structure
 * }
 * </pre>
 *
 * There is no corresponding JMeter construct. The alternative solution might be {@link net.simonix.dsl.jmeter.factory.controller.IncludeFactory}
 */
final class InsertFactory extends AbstractTestElementFragmentFactory {

}
