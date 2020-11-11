@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        arguments {
            argument(name: 'var_host', value: 'localhost')
        }

        defaults(protocol: 'http', domain: '${var_host}', port: 1080)

        group(users: 1000, rampUp: 300) {
            cookies(name: 'cookies manager')

            // insert login fragment
            insert 'fragments/login.groovy'

            http('GET /api/books') {
                params values: [ limit: '10' ]

                extract_json expressions: '$..id', variables: 'var_bookId'
            }

            http('GET /api/books/${var_bookId}') {
                extract_json expressions: '$..author.id', variables: 'var_authorId'
            }

            // simple form without parenthesis
            http 'GET /api/authors/${var_authorId}'

            // insert common fragment
            insert 'fragments/random_group.groovy'

            // insert other common fragment
            insert 'fragments/loop.groovy'
        }

        summary(file: 'script.jtl', enabled: true)

        // insert common configuration for backed listener
        insert 'fragments/backend.groovy'
    }
}

