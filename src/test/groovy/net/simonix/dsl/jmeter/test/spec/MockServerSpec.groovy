package net.simonix.dsl.jmeter.test.spec

import org.junit.Rule
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule
import spock.lang.Specification

class MockServerSpec extends Specification {
    @Rule
    MockServerRule mockServerRule = new MockServerRule(this, 8899)

    def MockServerClient mockServerClient
}
