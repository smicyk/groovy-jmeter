package net.simonix.dsl.jmeter.test.spec

import net.simonix.dsl.jmeter.test.sampler.LogSamplerFactory
import net.simonix.dsl.jmeter.test.sampler.TestElementListener
import net.simonix.dsl.jmeter.test.sampler.TestElementLogger
import spock.lang.Specification

class LogSamplerSpec extends Specification {
    def listener
    def plugins = [ 'log': new LogSamplerFactory('Log Sampler')]

    def setup() {
        listener = Mock(TestElementListener)
        TestElementLogger.setListener(listener)
    }

    def cleanup() {
        TestElementLogger.setListener(null)
    }
}
