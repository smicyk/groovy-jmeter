fragment {
    loop(count: 10) {
        execute_random {
            http('GET /api/books') {
                params values: [ search: 'New title', limit: '10', trackId: '${trackId}' ]
            }
        }
    }
}

