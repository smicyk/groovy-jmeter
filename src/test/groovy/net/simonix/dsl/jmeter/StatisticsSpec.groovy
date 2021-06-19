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
package net.simonix.dsl.jmeter

import net.simonix.dsl.jmeter.test.spec.MockServerSpec

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.run
import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response


class StatisticsSpec extends MockServerSpec {

    def "Run test with statistics check"() {
        given: 'Test plan and mock server'

        mockServerClient.when(
                request().withPath('/context/get')
        ).respond(
                response()
                        .withStatusCode(200)
        )

        def config = configure {
            plan {
                group(loops: 100) {
                    simple {
                        http('GET http://localhost:8899/context/get')
                    }

                    simple {
                        http('GET http://localhost:8899/context/sample', name: 'SAMPLE')
                    }
                }
            }
        }

        when: 'run the test'
        def statistics = run(config, true)

        then: 'check statistics for all samples'
        statistics.mean < 100
        statistics.standardDeviation < 5000
        statistics.min >= 0
        statistics.max < 100
        statistics.count == 200
        statistics.error == 0.5d
        statistics.throughput < 1000
        statistics.receivedBytes < 65000
        statistics.sentBytes < 120000
        statistics.averagePageBytes < 500
        statistics.totalBytes < 15000
        // elapsed time (in milliseconds) only for all samples
        statistics.elapsedTime < 2000

        and: 'check statistics for each sample'
        statistics.'GET /context/get'.mean < 100
        statistics.'GET /context/get'.standardDeviation < 5000
        statistics.'GET /context/get'.min >= 0
        statistics.'GET /context/get'.max < 100
        statistics.'GET /context/get'.count == 100
        statistics.'GET /context/get'.error == 0.0d
        statistics.'GET /context/get'.throughput < 1000
        statistics.'GET /context/get'.receivedBytes < 30000
        statistics.'GET /context/get'.sentBytes < 60000
        statistics.'GET /context/get'.averagePageBytes < 500
        statistics.'GET /context/get'.totalBytes < 7500

        statistics.'SAMPLE'.mean < 100
        statistics.'SAMPLE'.standardDeviation < 5000
        statistics.'SAMPLE'.min >= 0
        statistics.'SAMPLE'.max < 100
        statistics.'SAMPLE'.count == 100
        statistics.'SAMPLE'.error == 1.0d
        statistics.'SAMPLE'.throughput < 1000
        statistics.'SAMPLE'.receivedBytes < 40000
        statistics.'SAMPLE'.sentBytes < 75000
        statistics.'SAMPLE'.averagePageBytes < 500
        statistics.'SAMPLE'.totalBytes < 7500

        and: 'sanity check for total count and samples count'

        statistics.names.sum { name -> statistics."$name".count } == statistics.count
        // or
        statistics.values.sum {it.count } == statistics.count
    }
}