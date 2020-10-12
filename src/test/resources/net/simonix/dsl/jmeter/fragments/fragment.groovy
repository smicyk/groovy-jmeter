package net.simonix.dsl.jmeter.fragments

fragment {
    http('GET /books') {
        params {
            param(name: 'id', value: '1')
        }
    }
}