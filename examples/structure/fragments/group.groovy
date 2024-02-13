fragment {
    group loops: var_inner_loops, users: var_inner_users, {
        cookies(name: 'cookies manager')

        // insert login fragment
        insert 'fragments/login.groovy'

        http 'GET /api/books', {
            params values: [ limit: '10' ]
        }
    }
}