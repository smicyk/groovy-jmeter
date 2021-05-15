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
package net.simonix.dsl.jmeter.factory.config

import net.simonix.dsl.jmeter.test.spec.TempFileSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.save

class JdbcFactorySpec extends TempFileSpec {

    def "Check jdbc generation"() {
        given: 'Test plan with jdbc config element'
        def config = configure {
            plan {
                group {
                    jdbc datasource: 'myDataSource', {
                        pool connections: 0, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
                        connection url: 'test', driver: 'test', username: 'user', password: 'test', properties: ['test': 'test']
                        init(['ALTER SESSION SET CURRENT_SCHEMA = TEST', 'SET CHARACTERSET = UTF8'])
                        validation idle: true, timeout: 5000, query: '''
                            SELECT 1 FROM DUAL
                        '''
                    }

                    jdbc use: 'myDataSource', {
                        jdbc_preprocessor use: 'myDataSource', {
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

                        execute(result: 'var_result', inline: '''
                            UPDATE employee SET salary = ? WHERE id = ?
                        ''') {
                            params {
                                param value: 1200, type: 'INTEGER'
                                param value: 1, type: 'INTEGER'
                            }
                        }

                        jdbc_postprocessor use: 'myDataSource', {
                            query(limit: 1, timeout: 10, variables: ['var_id', 'var_name', 'var_salary'], inline: '''
                                SELECT id, name, salary FROM employee WHERE id = 1
                            ''')
                        }
                    }
                }
            }
        }

        File resultFile = tempFolder.newFile('jdbc_0.jmx')

        when: 'save test to file'
        save(config, resultFile)

        then: 'both files matches'
        filesAreTheSame('jdbc_0.jmx', resultFile)
    }
}
