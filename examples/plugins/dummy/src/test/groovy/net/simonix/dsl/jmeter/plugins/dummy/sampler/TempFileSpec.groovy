package net.simonix.dsl.jmeter.plugins.dummy.sampler

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
}