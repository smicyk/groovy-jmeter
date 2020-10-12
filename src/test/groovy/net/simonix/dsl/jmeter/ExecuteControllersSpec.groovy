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

class ExecuteControllersSpec extends LogSamplerSpec {

    def "'once' controller with two users"() {
        given: "Configure test plan with 'once' test element and two users"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2) {
                    execute(type: 'once') {
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

    def "'once' controller with two users and loop element"() {
        given: "Configure test plan with 'once' test element and two user with inner loop controller"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2, loops: 2) {
                    loop(count: 5) {
                        execute(type: 'once') {
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

        then: "the 'once' controller should be executed only once per user, per inner loop"

        4 * listener.listen('once')
        4 * listener.listen('twice')
        20 * listener.listen('ten')
    }

    def "'once' controller with two users and loops on group"() {
        given: "Configure test plan with 'once' test element and two user with loops on the group element"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(users: 2, loops: 5) {
                    execute(type: 'once') {
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

    def "'interleave' controller inside group test element with loops)"() {
        given: "Configure test plan with 'interleave' test element inside group with loops"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 3) {
                    execute(type: 'interleave') {
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

    def "nested 'interleave' controller inside group element with loops"() {
        given: "Configure test plan with nested 'interleave' test elements inside group with loops"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute(type: 'interleave') {
                        execute(type: 'interleave') {
                            log('first')
                            log('third')
                        }
                        execute(type: 'interleave') {
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

    def "'interleave' controller inside group element with nested simple controllers"() {
        given: "Configure test plan with 'interleave' test element and nested simple controllers inside group with loops"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 2) {
                    execute(type: 'interleave') {
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

    def "'interleave' controller with ignore subcontroller inside group element with nested simple controllers"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute(type: 'interleave', ignore: true) {
                        execute(type: 'interleave') {
                            log('first')
                            log('third')
                        }
                        execute(type: 'interleave') {
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

    def "'random' controller inside group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute(type: 'random') {
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

    def "'order' controller inside group element"() {
        given: "Configure test plan"

        def testPlan = configure(plugins: plugins) {
            plan {
                group(loops: 4) {
                    execute(type: 'order') {
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
                    execute(type: 'percent', percent: 0) {
                        log('0%')
                    }
                    execute(type: 'percent', percent: 25) {
                        log('20%')
                    }
                    execute(type: 'percent', percent: 40) {
                        log('40%')
                    }
                    execute(type: 'percent', percent: 60) {
                        log('60%')
                    }
                    execute(type: 'percent', percent: 80) {
                        log('80%')
                    }
                    execute(type: 'percent', percent: 100) {
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
                    execute(type: 'total', total: 0) {
                        log('0')
                    }
                    execute(type: 'total', total: 5) {
                        log('5')
                    }
                    execute(type: 'total', total: 10) {
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
                    execute(type: 'runtime', runtime: 1) {
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
                    execute(type: 'switch', value: 1) {
                        log('frist')
                        log('second')
                        log('third')
                    }
                    execute(type: 'switch', value: 'third') {
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
                    execute(type: 'if', condition: '__jexl3(${var_condition} == 5)') {
                        log('condition 1')
                    }

                    execute(type: 'if', condition: '__jexl3(${var_condition} == 5)', useExpression: true) {
                        log('condition 2')
                    }

                    execute(type: 'if', condition: '__groovy(vars.get("var_condition") == "5")', useExpression: true) {
                        log('condition 3')
                    }

                    execute(type: 'if', condition: '${var_condition} == 5', useExpression: false) {
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
                    execute(type: 'while', condition: 'var_condition1') {
                        jsrsampler('vars.put("var_condition1", "false")')

                        log('condition 1')
                    }

                    execute(type: 'while', condition: '__jexl3(${var_condition2} == true)') {
                        jsrsampler('vars.put("var_condition2", "false")')

                        log('condition 2')
                    }

                    execute(type: 'while', condition: '__groovy(vars.get("var_condition3") == "true")') {
                        jsrsampler('vars.put("var_condition3", "false")')

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