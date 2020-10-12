fragment {
    simple {
        random(minimum: 0, maximum: 10000, variable: 'var_random')
        execute_if('''__jexl3(vars.get('var_random') mod 2 == 0)''') {
            headers values: ['Content-Type': 'application/json']

            http('POST /api/books/${var_bookId}/comments') {
                body """\
                    {
                        "title": "Short comment",
                        "content": "Short content"
                    }
                    """.stripIndent()

                check_response {
                    status() eq 200
                }
            }
        }
    }
}