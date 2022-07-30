package net.simonix.dsl.jmeter.fragments

fragment {
    argument(name: 'influxdbUrl', value: 'http://localhost:8086/write?db=jmeter')
    argument(name: 'application', value: 'books')
    argument(name: 'measurement', value: 'performance')
    argument(name: 'summaryOnly', value: 'false')
    argument(name: 'samplersRegex', value: '.*')
    argument(name: 'percentiles', value: '90;95;99')
    argument(name: 'testTitle', value: 'books - users: 1000, rampup: 300')
    argument(name: "eventTags", value: '')
}