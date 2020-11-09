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
package net.simonix.dsl.jmeter

import net.simonix.dsl.jmeter.test.spec.LogSamplerSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.run

class ExecutionControllersSpec extends LogSamplerSpec {

    def "'once' controller inside group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2) {
                    execute_once {
                        log('once')
                    }
                    log('twice')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "the 'once' controller should be executed only once per user"

        2 * listener.listen('once')
        2 * listener.listen('twice')
    }

    def "'once' controller inside loop element in group"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2) {
                    loop(count: 5) {
                        execute_once {
                            log('once')
                        }
                        log('ten')
                    }
                    log('twice')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "the 'once' controller should be executed only once per user"

        2 * listener.listen('once')
        2 * listener.listen('twice')
        10 * listener.listen('ten')
    }

    def "'once' controller under group element with repeats"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2, loops: 5) {
                    execute_once {
                        log('once')
                    }
                    log('ten')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "the 'once' controller should be executed only once per user"

        2 * listener.listen('once')
        10 * listener.listen('ten')
    }

    def "'interleave' controller under group element (group with loops)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 3) {
                    execute_interleave {
                        log('first')
                        log('second')
                    }
                    log('interleaved')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "'first' and 'interleave' is from first loop"
        1 * listener.listen('first')
        then:
        1 * listener.listen('interleaved')

        then: "'second' and 'interleave' is from second loop"
        1 * listener.listen('second')
        then:
        1 * listener.listen('interleaved')

        then: "'first' and 'interleave' is from third loop"
        1 * listener.listen('first')
        then:
        1 * listener.listen('interleaved')
    }

    def "nested 'interleave' controller under group element (group with loops)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute_interleave {
                        execute_interleave {
                            log('first')
                            log('third')
                        }
                        execute_interleave {
                            log('second')
                            log('fourth')
                        }
                    }
                    log('interleaved')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "'first' and 'interleaved' is from first loop (first sub interleave)"
        1 * listener.listen('first')
        then:
        1 * listener.listen('interleaved')

        then: "'second' and 'interleaved' is from second loop (second sub interleave)"
        1 * listener.listen('second')
        then:
        1 * listener.listen('interleaved')

        then: "'third' and 'interleaved' is from third loop (first sub interleave)"
        1 * listener.listen('third')
        then:
        1 * listener.listen('interleaved')

        then: "'fourth' and 'interleaved' is from third loop (second sub interleave"
        1 * listener.listen('fourth')
        then:
        1 * listener.listen('interleaved')
    }

    def "'interleave' controller under group element (with nested simple controllers)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 2) {
                    execute_interleave {
                        simple {
                            log('first')
                            log('second')
                        }
                        simple {
                            log('third')
                            log('fourth')
                        }
                    }
                    log('interleaved')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "'first', 'second' and 'interleaved' is from first loop (first sub simple)"
        1 * listener.listen('first')
        then:
        1 * listener.listen('second')
        then:
        1 * listener.listen('interleaved')

        then: "'thrid', 'fourth' and 'interleaved' is from second loop (second sub simple)"
        1 * listener.listen('third')
        then:
        1 * listener.listen('fourth')
        then:
        1 * listener.listen('interleaved')
    }

    def "'interleave' controller with ignore subcontroller under group element (with nested simple controllers)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute_interleave(ignore: true) {
                        execute_interleave {
                            log('first')
                            log('third')
                        }
                        execute_interleave {
                            log('second')
                            log('fourth')
                        }
                    }
                    log('interleaved')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "'first' and 'interleaved' is from first loop (first sub interleave)"
        1 * listener.listen('first')
        then:
        1 * listener.listen('interleaved')

        then: "'second' and 'interleaved' is from second loop (second sub interleave)"
        1 * listener.listen('second')
        then:
        1 * listener.listen('interleaved')

        then: "'third' and 'interleaved' is from third loop (first sub interleave)"
        1 * listener.listen('third')
        then:
        1 * listener.listen('interleaved')

        then: "'fourth' and 'interleaved' is from third loop (second sub interleave"
        1 * listener.listen('fourth')
        then:
        1 * listener.listen('interleaved')
    }

    def "'random' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute_random {
                        log('first')
                        log('second')
                        log('third')
                        log('fourth')
                    }
                    log('interleaved')
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "all samples are executed randomly (can be zero up to 4), the order is unknown, all are interleaved with last sample"
        (0..4) * listener.listen('first')
        (0..4) * listener.listen('second')
        (0..4) * listener.listen('thrid')
        (0..4) * listener.listen('fourth')
        4 * listener.listen('interleaved')
    }

    def "'order' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute_order {
                        log('first')
                        log('second')
                        log('third')
                        log('fourth')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "in each pass only one sample is executed (choosen randomly)"
        (0..4) * listener.listen('first')
        (0..4) * listener.listen('second')
        (0..4) * listener.listen('thrid')
        (0..4) * listener.listen('fourth')
    }

    def "'percent' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 10) {
                    execute_percent(0) {
                        log('0%')
                    }
                    execute_percent(25) {
                        log('20%')
                    }
                    execute_percent(40) {
                        log('40%')
                    }
                    execute_percent(60) {
                        log('60%')
                    }
                    execute_percent(80) {
                        log('80%')
                    }
                    execute_percent(100) {
                        log('100%')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "in each pass only one sample is executed (choosen randomly)"
        0 * listener.listen('0%')
        2 * listener.listen('20%')
        4 * listener.listen('40%')
        6 * listener.listen('60%')
        8 * listener.listen('80%')
        10 * listener.listen('100%')
    }

    def "'total' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 10) {
                    execute_total(0) {
                        log('0')
                    }
                    execute_total(5) {
                        log('5')
                    }
                    execute_total(10) {
                        log('10')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "in each pass only one sample is executed (choosen randomly)"
        0 * listener.listen('0')
        5 * listener.listen('5')
        10 * listener.listen('10')
    }

    def "'runtime' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group {
                    execute_runtime(1) {
                        log('1s')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "at least one sample should be executed"
        (0.._) * listener.listen('1s')
    }

    def "'switch' controller under group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 3) {
                    execute_switch(1) {
                        log('frist')
                        log('second')
                        log('third')
                    }
                    execute_switch('third') {
                        log('frist')
                        log('second')
                        log('third')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "only selected samples will be activated"
        3 * listener.listen('second')
        3 * listener.listen('third')
    }

    def "conditional test element with positive evaluation (if controller)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                arguments {
                    argument(name: 'var_condition', value: '5')
                }

                group {
                    execute_if('__jexl3(${var_condition} == 5)') {
                        log('condition 1')
                    }

                    execute_if('__jexl3(${var_condition} == 5)', useExpression: true) {
                        log('condition 2')
                    }

                    execute_if('__groovy(vars.get("var_condition") == "5")', useExpression: true) {
                        log('condition 3')
                    }

                    execute_if('${var_condition} == 5', useExpression: false) {
                        log('condition 4')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "all conditions should be executed"

        1 * listener.listen('condition 1')
        1 * listener.listen('condition 2')
        1 * listener.listen('condition 3')
        1 * listener.listen('condition 4')
    }

    def "conditional test element with positive evaluation (while controller)"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                arguments {
                    argument(name: 'var_condition1', value: 'true')
                    argument(name: 'var_condition2', value: 'true')
                    argument(name: 'var_condition3', value: 'true')
                }

                group {
                    execute_while('var_condition1') {
                        jsrsampler(inline: 'vars.put("var_condition1", "false")')

                        log('condition 1')
                    }

                    execute_while('__jexl3(${var_condition2} == true)') {
                        jsrsampler(inline: 'vars.put("var_condition2", "false")')

                        log('condition 2')
                    }

                    execute_while('__groovy(vars.get("var_condition3") == "true")') {
                        jsrsampler(inline: 'vars.put("var_condition3", "false")')

                        log('condition 3')
                    }
                }
            }
        }

        when: "run the test"
        run(testPlan)

        then: "all condition should be executed"

        1 * listener.listen('condition 1')
        1 * listener.listen('condition 2')
        1 * listener.listen('condition 3')
    }
}
