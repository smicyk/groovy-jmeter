package net.simonix.dsl.jmeter.fragments

fragment {
    http('GET /books') {
        params {
            param(name: 'id', value: '1')
        }
    }

    http('GET /authors')

    simple {
        http('GET /comments') {
            params {
                param(name: 'authorId', value: '1')
            }
        }
    }

    loop {
        jdbc datasource: 'postgres', {
            pool connections: 10, wait: 1000, eviction: 60000, autocommit: true, isolation: 'DEFAULT', preinit: true
            connection url: 'jdbc:postgresql://database-db:5432/', driver: 'org.postgresql.Driver', username: 'postgres', password: 'postgres'
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
    }
}