fragment {
    http('POST /login') {
        params {
            param(name: 'username', value: 'john')
            param(name: 'password', value: 'john')
        }

        extract_regex expression: '"trackId", content="([0-9]+)"', variable: 'var_trackId'
    }
}