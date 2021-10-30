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
import org.mockserver.model.Delay
import org.mockserver.model.MediaType
import org.mockserver.verify.VerificationTimes

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import static net.simonix.dsl.jmeter.TestScriptRunner.configure
import static net.simonix.dsl.jmeter.TestScriptRunner.run
import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response
import static org.mockserver.verify.VerificationTimes.once

class HttpSamplerSpec extends MockServerSpec {

    def "HTTP request without params (defaults values)"() {
        when: "use only default values"

        def testPlan = configure {
            plan {
                group {
                    http(protocol: 'http', domain: 'localhost', path: '/simple', port: 8899, method: 'GET')
                    http(protocol: 'https', domain: 'localhost', path: '/secured', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "only one sample is created"
        
        mockServerClient.verify(request('/simple').withMethod('GET'), once())
        mockServerClient.verify(request('/secured').withMethod('GET'), once())
    }
    
    def "HTTP request with params"() {
        when: "add params to request"
        
        def testPlan = configure {
            plan {
                group {
                    http(protocol: 'http', domain: 'localhost', path: '/params', port: 8899, method: 'GET') {
                        params {
                            param(name: 'param1', value: 'value1')
                            param(name: 'param2', value: '3')
                            param(name: 'param3', value: 'not encoded value', encoded: false)
                            param(name: 'param4', value: 'not encoded value')
                            param(name: 'param5', value: 'encoded+value', encoded: true)
                        }
                    }
                }
            }
        }

        run(testPlan)

        then: "all params are send"
        
        mockServerClient.verify(request('/params')
                .withMethod('GET')
                .withQueryStringParameter('param1', 'value1')
                .withQueryStringParameter('param2', '3')
                .withQueryStringParameter('param3', 'not encoded value')
                .withQueryStringParameter('param4', 'not encoded value')
                .withQueryStringParameter('param5', 'encoded value'),
                once()
        )
    }
    
    def "HTTP request with defaults"() {
        when: "add params to request"
        
        def testPlan = configure {
            plan {
                group {
                    defaults(protocol: 'http', domain: 'localhost', path: '/default', port: 8899, method: 'GET') {
                        params {
                            param(name: 'param1', value: 'default')
                        }
                    }
                    
                    http()
                    
                    http(path: '/custom', method: 'GET') {
                        params {
                            param(name: 'param1', value: 'value1')
                            param(name: 'param2', value: '3')
                        }
                    }
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"
        
        mockServerClient.verify(
                request('/default')
                        .withMethod('GET')
                        .withQueryStringParameter('param1', 'default'),
                request('/custom')
                        .withMethod('GET')
                        .withQueryStringParameter('param1', 'value1')
                        .withQueryStringParameter('param2', '3'))
    }
    
    def "HTTP request with authorization element"() {
        when: "add params to request"
        
        def testPlan = configure {
            plan {
                group {
                    authorizations {
                        authorization(url: 'http://localhost:8899', username: 'username', password: 'password', mechanism: 'BASIC')
                    }

                    http(protocol: 'http', domain: 'localhost', path: '/resource', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"
        
        mockServerClient.verify(request('/resource')
                .withMethod('GET')
                .withHeader('Authorization', 'Basic dXNlcm5hbWU6cGFzc3dvcmQ='),
                once()
        )
    }
    
    def "HTTP request with cache handles expired resource"() {
        given: "define cached resource which is already expired"

        ZonedDateTime date = ZonedDateTime.now()
        ZonedDateTime lastModified = date.minusDays(30)
        ZonedDateTime expires = date.minusSeconds(7200)

        mockServerClient.when(
                request().withPath('/cached')
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader('Date', DateTimeFormatter.RFC_1123_DATE_TIME.format(date))
                        .withHeader('Last-Modified', DateTimeFormatter.RFC_1123_DATE_TIME.format(lastModified))
                        .withHeader('Expires', DateTimeFormatter.RFC_1123_DATE_TIME.format(expires))
                        .withHeader('ETag', '--my-e-tag--')
                        .withBody('value to cache', MediaType.PLAIN_TEXT_UTF_8)
        )

        when: "use default cache control using control-cache/expires headers"

        def testPlan = configure {
            plan {
                group {
                    cache()

                    http(protocol: 'http', domain: 'localhost', path: '/cached', port: 8899, method: 'GET')
                    http(protocol: 'http', domain: 'localhost', path: '/cached', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"

        mockServerClient.verify(
                request('/cached')
                        .withMethod('GET'),
                request('/cached')
                        .withMethod('GET')
                        .withHeader('If-Modified-Since', DateTimeFormatter.RFC_1123_DATE_TIME.format(lastModified))
                        .withHeader('If-None-Match', '--my-e-tag--'))
    }

    def "HTTP request with cache handles not expired resource"() {
        given: "define cached resource which is not expired"

        ZonedDateTime date = ZonedDateTime.now()
        ZonedDateTime lastModified = date.minusDays(30)
        ZonedDateTime expires = date.plusSeconds(7200)

        mockServerClient.when(
                request().withPath('/cached')
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader('Cache-Control', 'max-age=7200')
                        .withHeader('Date', DateTimeFormatter.RFC_1123_DATE_TIME.format(date))
                        .withHeader('Last-Modified', DateTimeFormatter.RFC_1123_DATE_TIME.format(lastModified))
                        .withHeader('Expires', DateTimeFormatter.RFC_1123_DATE_TIME.format(expires))
                        .withHeader('ETag', '--my-e-tag--')
                        .withBody('value to cache', MediaType.PLAIN_TEXT_UTF_8)
        )

        when: "use default cache control using control-cache/expires headers"

        def testPlan = configure {
            plan {
                group {
                    cache()

                    http(protocol: 'http', domain: 'localhost', path: '/cached', port: 8899, method: 'GET')
                    http(protocol: 'http', domain: 'localhost', path: '/cached', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"

        mockServerClient.verify(
                request('/cached')
                        .withMethod('GET'),
                once()
        )
    }

    def "HTTP request with cookies element"() {
        given: "define cached resource"
        
        mockServerClient.when(
                request().withPath('/resource')
        ).respond(
                response()
                        .withStatusCode(200)
                        .withCookie('MYCOOKIE', 'somevalue')
                        .withBody('value to cache', MediaType.PLAIN_TEXT_UTF_8)
        )
        
        when: "add params to request"
        
        def testPlan = configure {
            plan {
                group {
                    cookies(policy: 'standard') {
                        cookie(name: 'HARDCODED', value: 'value', domain: 'localhost', path: '/resource')
                    }
                    
                    http(protocol: 'http', domain: 'localhost', path: '/resource', port: 8899, method: 'GET')
                    http(protocol: 'http', domain: 'localhost', path: '/resource', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"
        
        mockServerClient.verify(
                request('/resource')
                        .withMethod('GET')
                        .withCookie('HARDCODED', 'value'),
                request('/resource')
                        .withMethod('GET')
                        .withCookie('HARDCODED', 'value')
                        .withCookie('MYCOOKIE', 'somevalue'))
    }
    
    def "HTTP request with headers element"() {
        when: "add params to request"
        
        def testPlan = configure {
            plan {
                group {
                    headers {
                        header(name: 'X-GLOBAL-VALUE', value: 'global')
                    }
                    
                    http(protocol: 'http', domain: 'localhost', path: '/resource', port: 8899, method: 'GET') {
                        headers {
                            header(name: 'X-LOCAL-VALUE', value: 'local')
                        }
                    }
                    http(protocol: 'http', domain: 'localhost', path: '/resource', port: 8899, method: 'GET')
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"
        
        mockServerClient.verify(
                request('/resource')
                        .withMethod('GET')
                        .withHeader('X-GLOBAL-VALUE', 'global')
                        .withHeader('X-LOCAL-VALUE', 'local'),
                request('/resource')
                        .withMethod('GET')
                        .withHeader('X-GLOBAL-VALUE', 'global'))
    }

    def "HTTP request short version"() {
        when: "add params to request"

        def testPlan = configure {
            plan {
                group {
                    simple {
                        http('GET http://localhost:8899/protocol')
                    }

                    simple {
                        defaults(port: 8899)

                        http('GET http://localhost/protocol')
                    }

                    simple {
                        defaults(protocol: 'http')

                        http('GET localhost:8899/hostname')
                    }

                    simple {
                        defaults(protocol: 'http', port: 8899)

                        http('GET localhost/hostname')
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost')

                        http('GET :8899/port')
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost', port: 8899)

                        http('GET /path')
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost', port: 8899)

                        http('GET /path') {
                            params values: [
                                    'param1': 'value1'
                            ]
                        }
                    }

                    simple {
                        http('GET http://localhost:8899')
                    }

                    simple {
                        defaults(port: 8899)

                        http('GET http://localhost')
                    }

                    simple {
                        defaults(protocol: 'http')

                        http('GET localhost:8899')
                    }

                    simple {
                        defaults(protocol: 'http', port: 8899)

                        http('GET localhost')
                    }
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"

        mockServerClient.verify(
                request('/protocol')
                        .withMethod('GET'),
                request('/protocol')
                        .withMethod('GET'),
                request('/hostname')
                        .withMethod('GET'),
                request('/hostname')
                        .withMethod('GET'),
                request('/port')
                        .withMethod('GET'),
                request('/path')
                        .withMethod('GET'),
                request('/path')
                        .withMethod('GET')
                        .withQueryStringParameter('param1', 'value1'),
                request().withMethod('GET'),
                request().withMethod('GET'))
    }

    def "HTTP request short version (complex paths)"() {
        when: "add params to request"

        def testPlan = configure {
            plan {
                group {
                    simple {
                        http('GET http://localhost:8899/complex-path/with.special/cha_racters') {
                            check_response {
                                status() eq 200
                            }
                        }
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost', port: 8899)

                        http('GET /complex-path/with.special/cha_racters') {
                            params values: [
                                    'param1': 'value1'
                            ]

                            check_response {
                                status() eq 200
                            }
                        }
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost', port: 8899)

                        variables values: [
                                'var_id': '123456'
                        ]

                        http('GET /variable/${var_id}') {
                            check_response {
                                status() eq 200
                            }
                        }
                    }
                }
            }
        }

        run(testPlan)

        then: "we get request with default values and with custom"

        mockServerClient.verify(
                request('/complex-path/with.special/cha_racters')
                        .withMethod('GET'),
                request('/complex-path/with.special/cha_racters')
                        .withMethod('GET')
                        .withQueryStringParameter('param1', 'value1'),
                request('/variable/123456')
                        .withMethod('GET'))
    }

    def "HTTP request embedded resources"() {
        given: "simple page with embedded resources"

        mockServerClient.when(
                request().withPath('/index.html')
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody('''\
                        <html>
                            <head>
                                <script src="/assets/js/index.js" type="text/javascript"></script>
                                <script src="/assets/js/table.js" type="text/javascript"></script>
                                <link href="/assets/css/main.css" rel="stylesheet" type="text/css">
                            </head>
                            <body>
                                <p>Hello</p>
                                <img src="/assets/images/hello.png"></img>
                            </body>
                        </html>
                        ''', MediaType.TEXT_HTML)
        )

        when: "make request with different resource configurations"

        def testPlan = configure {
            plan {
                group {
                    simple {
                        http('GET http://localhost:8899/index.html') {
                            resources urlInclude: 'http://localhost:8899/assets/js/.*'
                        }
                    }

                    simple {
                        defaults(protocol: 'http', domain: 'localhost', port: 8899) {
                            resources urlInclude: 'http://localhost:8899/assets/.*', urlExclude: '.*\\.(js)'
                        }

                        // download everything excluding *.js files
                        http('GET /index.html')

                        // override defaults (go only for images, but still urlExclude is to exclude *.js files)
                        http('GET /index.html') {
                            resources parallel: 1, urlInclude: 'http://localhost:8899/assets/images/.*'
                        }
                    }
                }
            }
        }

        run(testPlan)

        then: "get resources for each index.html call"

        mockServerClient.verify(
                request('/index.html')
                        .withMethod('GET'),
                request('/assets/js/index.js')
                        .withMethod('GET'),
                request('/assets/js/table.js')
                        .withMethod('GET'),
                request('/index.html')
                        .withMethod('GET'),
                request('/assets/css/main.css')
                        .withMethod('GET'),
                request('/assets/images/hello.png')
                        .withMethod('GET'),
                request('/index.html')
                        .withMethod('GET'),
                request('/assets/images/hello.png')
                        .withMethod('GET'),
        )
    }

    def "HTTP request with timeouts"() {
        given: "slow server with simple page"

        mockServerClient.when(
                request().withPath('/index.html')
        ).respond(
                response()
                        .withStatusCode(200)
                        .withDelay(Delay.milliseconds(1000))
        )

        when: "make requests with timeout configuration"

        def testPlan = configure {
            plan {
                group {
                    simple {
                        http('GET http://localhost:8899/index.html') {
                            timeout response: 500
                        }
                    }

                    simple {
                        defaults {
                            timeout response: 250
                        }

                        http('GET http://localhost:8899/index.html')
                    }

                    // those should pass
                    simple {
                        defaults {
                            timeout response: 1250
                        }

                        http('GET http://localhost:8899/index.html')
                        http('GET http://localhost:8899/index.html')
                    }
                }
            }
        }

        var statistics = run(testPlan, true)

        then: "call is executed but with error"

        statistics.count == 4
        statistics.error == 0.5d
        mockServerClient.verify(
                request('/index.html')
                        .withMethod('GET'),
                VerificationTimes.exactly(4)
        )
    }
}