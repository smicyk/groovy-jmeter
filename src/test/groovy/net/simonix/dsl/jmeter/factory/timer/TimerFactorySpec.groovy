package net.simonix.dsl.jmeter.factory.timer

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class TimerFactorySpec extends TempFileSpec {
    def "Check default timers generation"() {
        given: 'Test plan with timers element'
        def config = configure {
            plan {
                // random timers
                constant_timer()
                uniform_timer()
                poisson_timer()
                gaussian_timer()

                // throughput
                constant_throughput()
                precise_throughput()

                // synchronizing
                synchronizing_timer()
            }
        }

        File resultFile = tempFolder.newFile('timers_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('timers_0.jmx', resultFile)
    }

    def "Check timers generation"() {
        given: 'Test plan with timers element'
        def config = configure {
            plan {
                // random timers
                constant_timer(delay: 500)
                uniform_timer(delay: 500, range: 1000)
                poisson_timer(delay: 500, range: 1000)
                gaussian_timer(delay: 500, range: 1000)

                // throughput
                constant_throughput(target: 200, basedOn: 'all_users')
                precise_throughput(target: 200, period: 1000, duration: 1000, batchUsers: 10, batchDelay: 10, samples: 1000, percents: 50, seed: 100000)

                // synchronizing
                synchronizing_timer(users: 100, timeout: 1000)
            }
        }

        File resultFile = tempFolder.newFile('timers_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('timers_1.jmx', resultFile)
    }
}