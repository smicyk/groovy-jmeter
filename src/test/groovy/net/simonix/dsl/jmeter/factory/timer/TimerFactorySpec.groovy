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

    def "Check default timers generation with timer and throughput"() {
        given: 'Test plan with timers element'
        def config = configure {
            plan {
                // random timers

                timer type: 'constant'
                timer type: 'uniform'
                timer type: 'poisson'
                timer type: 'gaussian'

                // throughput
                throughput type: 'constant'
                throughput type: 'precise'

                // synchronizing
                timer type: 'synchronizing'
            }
        }

        File resultFile = tempFolder.newFile('timers_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('timers_2.jmx', resultFile)
    }

    def "Check timers generation with expressions"() {
        given: 'Test plan with timers element'
        def config = configure {
            plan {
                // variables
                variables values: [
                        'var_delay': 500,
                        'var_range': 1000,

                        'var_target': 200,
                        'var_basedOn': 'all_users',

                        'var_period': 1000,
                        'var_duration': 1000,
                        'var_batchUsers': 10,
                        'var_batchDelay': 10,
                        'var_samples': 1000,
                        'var_percent': 50,
                        'var_seed': 100000,

                        'var_users': 100,
                        'var_timeout': 1000
                ]
                // random timers
                constant_timer(delay: '${var_delay}')
                uniform_timer(delay: '${var_delay}', range: '${var_range}')
                poisson_timer(delay: '${var_delay}', range: '${var_range}')
                gaussian_timer(delay: '${var_delay}', range: '${var_range}')

                // throughput
                constant_throughput(target: '${var_target}', basedOn: '${var_basedOn}')
                precise_throughput(target: '${var_target}', period: '${var_period}', duration: '${var_duration}', batchUsers: '${var_batchUsers}', batchDelay: '${var_batchDelay}', samples: '${var_samples}', percents: '${var_percent}', seed: '${var_seed}')

                // synchronizing
                synchronizing_timer(users: '${var_users}', timeout: '${var_timeout}')
            }
        }

        File resultFile = tempFolder.newFile('timers_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('timers_3.jmx', resultFile)
    }
}
