/*
 * Copyright 2021 Szymon Micyk
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
package net.simonix.dsl.jmeter.factory.sampler

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class GraphQLFactorySpec extends TempFileSpec {

    def "Check default graphql generation"() {
        given: 'Test plan with graphql element'
        def config = configure {
            plan {
                group {
                    graphql()
                    graphql(name: 'Factory Http', comments: "Factory Comment", enabled: false)
                    graphql(protocol: 'http', domain: 'example.com', port: 80, path: '/graphql', method: 'GET', encoding: 'UTF-8', autoRedirects: true, followRedirects: false, keepAlive: false, impl: 'java', saveAsMD5: true) {
                        operation 'MyOperation'
                        execute '''
                            mutation {
                              createEmployee(
                                input: {
                                  employee: {
                                    id: $id
                                    firstName: $firstName
                                    lastName: $lastName
                                    email: $email
                                    salary: $salary
                                  }
                                }
                              ) {
                                employee {
                                  id
                                }
                              }
                            }
                        '''

                        variables '''
                            {
                              id: "${var_id}"
                              firstName: "${var_first_name}"
                              lastName: "${var_last_name}"
                              email: "${var_email}"
                              salary: ${var_salary}
                            }
                        '''
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('graphql_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('graphql_0.jmx', resultFile)
    }

    def "Check graphql proxy generation"() {
        given: 'Test plan with graphql element'
        def config = configure {
            plan {
                graphql 'POST http://www.example.com/graphql', {
                    proxy scheme: 'http', host: 'my.proxy.com', port: '80', username: 'test', password: 'test'
                }

                graphql 'POST http://www.example.com/graphql', {
                    proxy 'http://my.proxy.com:80', username: 'test', password: 'test'
                }

                graphql 'POST http://www.example.com/graphql', {
                    proxy 'http://my.proxy.com:80'
                }

                graphql 'POST http://www.example.com/graphql', {
                    proxy 'my.proxy.com:80'
                }

                graphql 'POST http://www.example.com/graphql', {
                    proxy 'my.proxy.com'
                }
            }
        }

        File resultFile = tempFolder.newFile('graphql_1.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('graphql_1.jmx', resultFile)
    }

    def "Check graphql source generation"() {
        given: 'Test plan with graphql element and source element'

        def config = configure {
            plan {
                graphql 'GET http://www.example.com/graphql', {
                    source type: 'hostname', address: 'example.com'
                }

                graphql 'GET http://www.example.com/graphql', {
                    source type: 'device', address: 'eth0'
                }

                graphql 'GET http://www.example.com/graphql', {
                    source type: 'deviceIp4', address: 'eth0'
                }

                graphql 'GET http://www.example.com/graphql', {
                    source type: 'deviceIp6', address: 'eth0'
                }
            }
        }

        File resultFile = tempFolder.newFile('graphql_2.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('graphql_2.jmx', resultFile)
    }

    def "Check graphql timeout generation"() {
        given: 'Test plan with graphql element and timeout element'
        def config = configure {
            plan {
                graphql 'GET http://www.example.com/graphql', {
                    timeout connect: 5000, response: 10000
                }
            }
        }

        File resultFile = tempFolder.newFile('graphql_3.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('graphql_3.jmx', resultFile)
    }
}
