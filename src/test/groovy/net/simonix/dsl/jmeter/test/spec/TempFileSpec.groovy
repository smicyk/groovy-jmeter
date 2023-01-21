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
package net.simonix.dsl.jmeter.test.spec

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import org.xmlunit.diff.Diff
import spock.lang.Specification

/**
 * Test specification with temporary folder rule. Use when testing jmx files.
 */
class TempFileSpec extends Specification {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder()

    void filesAreTheSame(String expected, File result) {
        Diff d = DiffBuilder.compare(this.class.getResourceAsStream(expected))
                .withTest(Input.fromFile(result))
                .ignoreComments()
                .ignoreWhitespace()
                .build()

        assert !d.hasDifferences()
    }

    void filesAreTheSame(File expected, File result) {
        Diff d = DiffBuilder.compare(expected)
                .withTest(Input.fromFile(result))
                .ignoreComments()
                .ignoreWhitespace()
                .build()

        assert !d.hasDifferences()
    }
}
