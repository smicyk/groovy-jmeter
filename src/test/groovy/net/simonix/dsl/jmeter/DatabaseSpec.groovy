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
package net.simonix.dsl.jmeter

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.run

class DatabaseSpec extends Specification {

    @Shared sql = Sql.newInstance(url: 'jdbc:h2:mem:testDB', driver: 'org.h2.Driver', user: 'sa', password: 'sa')

    def "Run JDBC sampler"() {
        given: "Configure test plan with database test element"

        def testPlan = configure {
            plan {
                group {
                    jdbc datasource: 'local', {
                        pool connections: 10, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
                        connection url: 'jdbc:h2:mem:testDB', driver: 'org.h2.Driver', username: 'sa', password: 'sa', properties: [
                                'MODE': 'PostgreSQL',
                                'AUTO_SERVER_PORT': '1530'
                        ]
                        init(["SET @USER = 'Joe'"])
                        validation idle: true, timeout: 5000, query: '''
                            SELECT 1
                        '''
                    }

                    jdbc use: 'local', {
                        execute '''
                            CREATE TABLE employee (id INTEGER PRIMARY KEY, name VARCHAR(50), salary INTEGER, department VARCHAR(50))
                        '''
                    }

                    jdbc use: 'local', {
                        execute(inline: '''
                            UPDATE employee SET salary = ? WHERE id = ?
                        ''') {
                            params {
                                param value: 1200, type: 'INTEGER'
                                param value: 1, type: 'INTEGER'
                            }
                        }

                        jdbc_preprocessor use: 'local', {
                            execute inline: '''
                                INSERT INTO employee (id, name, salary, department) VALUES(?, ?, ?, ?)
                            ''', {
                                params {
                                    param value: 1, type: 'INTEGER'
                                    param value: 'Joe', type: 'VARCHAR'
                                    param value: 1000, type: 'INTEGER'
                                    param value: 'OFFICE', type: 'VARCHAR'
                                }
                            }
                        }

                        jdbc_postprocessor use: 'local', {
                            query(inline: '''
                                SELECT salary FROM employee WHERE id = 1
                            ''', variables: [ 'var_employee_salary' ])
                        }
                    }

                    // variables has prefix depending on row number
                    execute_if '''__groovy(vars.get('var_employee_salary_1') == "1200")''', {
                        jdbc use: 'local', {
                            execute '''
                                INSERT INTO employee (id, name, salary, department) VALUES(?, ?, ?, ?)
                            ''', {
                                params {
                                    param value: 2, type: 'INTEGER'
                                    param value: 'John', type: 'VARCHAR'
                                    param value: 1500, type: 'INTEGER'
                                    param value: 'OFFICE', type: 'VARCHAR'
                                }
                            }
                        }
                    }

                    jdbc use: 'local', {
                        query(limit: 10, timeout: 10, result: 'var_result', variables: ['var_id', 'var_name', 'var_salary'], inline: '''
                            SELECT id, name, salary FROM employee WHERE department = ?
                        ''') {
                            params {
                                param value: 'OFFICE', type: 'VARCHAR'
                            }
                        }
                    }
                }
            }
        }

        when: "run the test"

        run(testPlan)

        def r1 = sql.rows("SELECT id FROM employee WHERE id = 1")
        def r2 = sql.rows("SELECT id FROM employee WHERE id = 2")

        then: "the unique record should be selected"
        r1.size == 1
        r1.first().id == 1

        r2.size == 1
        r2.first().id == 2
    }
}
