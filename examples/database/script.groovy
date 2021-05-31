@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')
@Grab('org.postgresql:postgresql:42.2.20')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    // define basic test plan
    plan {
        before {
            jdbc datasource: 'postgres', {
                pool connections: 10, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
                connection url: 'jdbc:postgresql://database-db:5432/', driver: 'org.postgresql.Driver', username: 'postgres', password: 'postgres'
//            init(["SET @USER = 'Joe'"])
                validation idle: true, timeout: 5000, query: '''SELECT 1'''
            }

            jdbc use: 'postgres', {
                jdbc_preprocessor use: 'postgres', {
                    execute('''
                        DROP TABLE employee
                    ''')
                }

                execute('''
                    CREATE TABLE employee (id INTEGER PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50), email VARCHAR(50), salary INTEGER)
                ''')
            }

            jdbc use: 'postgres', {
                execute('''
                    CREATE OR REPLACE PROCEDURE fireEmployee(in_id IN EMPLOYEE.ID%TYPE, out_result INOUT VARCHAR)
                    LANGUAGE plpgsql
                    as $$
                    BEGIN
                        DELETE FROM employee WHERE id = in_id;
                        commit;
                        
                        out_result := 'TRUE';
  
                    EXCEPTION
                        WHEN OTHERS THEN 
                            out_result := 'FALSE';
                        ROLLBACK;
                    END; $$
                ''')
            }
        }

        group users: 100, rampUp: 60, loops: 10, {
            csv file: 'employees.csv', delimiter: ',', ignoreFirstLine: true, variables: ['var_id', 'var_first_name', 'var_last_name', 'var_email', 'var_salary']

            jdbc use: 'postgres', {
                jdbc_preprocessor use: 'postgres', {
                    execute('''
                        INSERT INTO employee (id, first_name, last_name, email, salary) VALUES(?, ?, ?, ?, ?)
                    ''') {
                        params {
                            param value: '${var_id}', type: 'INTEGER'
                            param value: '${var_first_name}', type: 'VARCHAR'
                            param value: '${var_last_name}', type: 'VARCHAR'
                            param value: '${var_email}', type: 'VARCHAR'
                            param value: '${var_salary}', type: 'INTEGER'
                        }
                    }
                }

                execute ('''
                    UPDATE employee SET salary = ? WHERE id = ?
                ''') {
                    params {
                        param value: '${__groovy(vars.get("var_salary") + 1000)}', type: 'INTEGER'
                        param value: '${var_id}', type: 'INTEGER'
                    }
                }

                jdbc_postprocessor use: 'postgres', {
                    query('''
                        SELECT id, salary FROM employee WHERE id = ?
                    ''', variables: ['var_updated_id', 'var_updated_salary']) {
                        params {
                            param value: '${var_id}', type: 'INTEGER'
                        }
                    }
                }
            }

            // variables has prefix depending on row number
            execute_if '''__groovy(vars.get('var_updated_salary_1').toInteger() > 6000)''', {
                jdbc use: 'postgres', name: 'Call FireEmployee', {
                    callable('''
                        CALL fireEmployee(?, ?)
                    ''') {
                        params {
                            param value: '${var_updated_id_1}', type: 'IN INTEGER'
                            param value: 'var_fire_success', type: 'INOUT VARCHAR'
                        }
                    }
                }
            }

            jdbc use: 'postgres', {
                query(limit: 1, result: 'var_employee_count', inline: '''
                    SELECT count(*) FROM employee
                ''')
            }

            // output to .jtl file
            summary(file: 'script.jtl', enabled: true)

            // gather execution information influxdb backend
            backend(name: 'InfluxDb Backend', enabled: true) {
                arguments {
                    argument(name: 'influxdbMetricsSender', value: 'org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender')
                    argument(name: 'influxdbUrl', value: 'http://influxdb-db:8086/write?db=jmeter')
                    argument(name: 'application', value: 'employees')
                    argument(name: 'measurement', value: 'performance')
                    argument(name: 'summaryOnly', value: 'false')
                    argument(name: 'samplersRegex', value: '.*')
                    argument(name: 'percentiles', value: '90;95;99')
                    argument(name: 'testTitle', value: 'employees - users: 100, rampup: 60')
                    argument(name: "eventTags", value: '')
                }
            }
        }
    }
}
