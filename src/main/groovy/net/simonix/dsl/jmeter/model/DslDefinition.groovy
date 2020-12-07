/*
 * Copyright 2020 Szymon Micyk
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
package net.simonix.dsl.jmeter.model

import groovy.transform.CompileDynamic

import static net.simonix.dsl.jmeter.model.constraint.Constraints.inList
import static net.simonix.dsl.jmeter.model.constraint.Constraints.range
import static DefinitionBuilder.keyword
import static DefinitionBuilder.properties

@CompileDynamic
final class DslDefinition {
    // plan
    static final Set<PropertyDefinition> COMMON_PROPERTIES = properties {
        property(name: 'name', required: false)
        property(name: 'comments', required: false, defaultValue: '')
        property(name: 'enabled', required: false, defaultValue: true)
    }

    static final KeywordDefinition PLAN = keyword('plan') {
        include(COMMON_PROPERTIES)
        property(name: 'serialized', required: false, defaultValue: false)
        property(name: 'functionalMode', required: false, defaultValue: false)
        property(name: 'tearDownOnShutdown', required: false, defaultValue: false)
    }

    // group
    static final KeywordDefinition GROUP = keyword('group') {
        include(COMMON_PROPERTIES)
        property(name: 'users', required: false, defaultValue: 1, constraints: range(1))
        property(name: 'rampUp', required: false, defaultValue: 1, constraints: range(1))
        property(name: 'delayedStart', required: false, defaultValue: false)
        property(name: 'scheduler', required: false, defaultValue: false)
        property(name: 'delay', required: false, defaultValue: 0, constraints: range(0))
        property(name: 'duration', required: false, defaultValue: 0, constraints: range(0))
        property(name: 'loops', required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', required: false, defaultValue: false)
    }

    // controllers
    static final KeywordDefinition LOOP = keyword('loop') {
        include(COMMON_PROPERTIES)
        property(name: 'count', required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SIMPLE = keyword('simple') {
        include(COMMON_PROPERTIES)
    }
    
    static final KeywordDefinition TRANSACTION = keyword('transaction') {
        include(COMMON_PROPERTIES)
        property(name: 'timers', required: false, defaultValue: false)
        property(name: 'generate', required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SECTION = keyword('section') {
        include(COMMON_PROPERTIES)
        property(name: 'lock', required: false, defaultValue: 'global_lock')
    }
    
    static final KeywordDefinition INCLUDE = keyword('include') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true)
    }
    
    static final KeywordDefinition FOR_EACH = keyword('for_each') {
        include(COMMON_PROPERTIES)
        property(name: 'in', required: false, defaultValue: '')
        property(name: 'out', required: false, defaultValue: '')
        property(name: 'separator', required: false, defaultValue: true)
        property(name: 'start', required: false, defaultValue: 0)
        property(name: 'end', required: false, defaultValue: 1)
    }
    
    static final KeywordDefinition EXECUTE = keyword('execute') {
        include(COMMON_PROPERTIES)
        property(name: 'type', required: true, constraints: inList(['if', 'while', 'once', 'interleave', 'random', 'order', 'percent', 'total', 'runtime', 'switch']))
    }

    static final KeywordDefinition EXECUTE_IF = keyword('execute_if') {
        include(COMMON_PROPERTIES)
        property(name: 'condition', required: true)
        property(name: 'useExpression', required: false, defaultValue: true)
        property(name: 'evaluateAll', required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_WHILE = keyword('execute_while') {
        include(COMMON_PROPERTIES)
        property(name: 'condition', required: true)
    }

    static final KeywordDefinition EXECUTE_ONCE = keyword('execute_once') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_INTERLEAVE = keyword('execute_interleave') {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', required: false, defaultValue: false)
        property(name: 'acrossUsers', required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_RANDOM = keyword('execute_random') {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_ORDER = keyword('execute_order') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_PERCENT = keyword('execute_percent') {
        include(COMMON_PROPERTIES)
        property(name: 'percent', required: false, defaultValue: 100, constraints: range(0, 100))
        property(name: 'perUser', required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_TOTAL = keyword('execute_total') {
        include(COMMON_PROPERTIES)
        property(name: 'total', required: false, defaultValue: 1, constraints: range(0))
        property(name: 'perUser', required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_RUNTIME = keyword('execute_runtime') {
        include(COMMON_PROPERTIES)
        property(name: 'runtime', required: false, defaultValue: 1, constraints: range(0))
    }

    static final KeywordDefinition EXECUTE_SWITCH = keyword('execute_switch') {
        include(COMMON_PROPERTIES)
        property(name: 'value', required: false, defaultValue: '0')
    }

    // samplers
    static final Set<PropertyDefinition> JSR223_PROPERTIES = properties {
        property(name: 'inline', required: false, defaultValue: '')
        property(name: 'cacheKey', required: false, defaultValue: true)
        property(name: 'file', required: false, defaultValue: '')
        property(name: 'parameters', required: false, defaultValue: '')
        property(name: 'language', required: false, defaultValue: 'groovy', constraints: inList(['groovy']))
    }

    static final Set<PropertyDefinition> HTTP_COMMON_PROPERTIES = properties {
        property(name: 'method', required: false, defaultValue: '')
        property(name: 'protocol', required: false, defaultValue: '')
        property(name: 'domain', required: false, defaultValue: '')
        property(name: 'port', required: false, constraints: range(1, 65535))
        property(name: 'path', required: false, defaultValue: '')
        property(name: 'encoding', required: false, defaultValue: '')
        property(name: 'autoRedirects', required: false, defaultValue: false)
        property(name: 'followRedirects', required: false, defaultValue: true)
        property(name: 'keepAlive', required: false, defaultValue: true)
        property(name: 'multipart', required: false, defaultValue: false)
        property(name: 'browserCompatibleMultipart', required: false, defaultValue: false)
    }

    static final KeywordDefinition HTTP = keyword('http') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'impl', required: false, defaultValue: '')
        property(name: 'connectTimeout', required: false, defaultValue: '')
        property(name: 'responseTimeout', required: false, defaultValue: '')
        property(name: 'downloadEmbeddedResources', required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', required: false, defaultValue: '')
        property(name: 'ipSource', required: false, defaultValue: '')
        property(name: 'ipSourceType', required: false, defaultValue: null, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', required: false, defaultValue: '')
        property(name: 'proxyHost', required: false, defaultValue: '')
        property(name: 'proxyPort', required: false, defaultValue: '')
        property(name: 'proxyUser', required: false, defaultValue: '')
        property(name: 'proxyPassword', required: false, defaultValue: '')
        property(name: 'saveAsMD5', required: false, defaultValue: false)
    }

    static final KeywordDefinition AJP = keyword('ajp') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'downloadEmbeddedResources', required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', required: false, defaultValue: '')
        property(name: 'saveAsMD5', required: false, defaultValue: false)
    }

    static final KeywordDefinition DEBUG = keyword('debug') {
        include(COMMON_PROPERTIES)
        property(name: 'displayJMeterProperties', required: false, defaultValue: false)
        property(name: 'displayJMeterVariables', required: false, defaultValue: false)
        property(name: 'displaySystemProperties', required: false, defaultValue: false)
    }

    static final KeywordDefinition JSR223_SAMPLER = keyword('jsrsampler') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition FLOW = keyword('flow') {
        include(COMMON_PROPERTIES)
        property(name: 'action', required: false, defaultValue: 'pause', constraints: inList(['pause', 'stop', 'stop_now', 'restart_next_loop', 'start_next', 'break']))
        property(name: 'target', required: false, defaultValue: 'current', constraints: inList(['current', 'all']))
        property(name: 'duration', required: false, defaultValue: '0', constraints: range(0))
    }

    // common
    static final KeywordDefinition PARAM = keyword('param') {
        property(name: 'name', required: false, defaultValue: null)
        property(name: 'value', required: false, defaultValue: null)
        property(name: 'encoded', required: false, defaultValue: false)
        property(name: 'encoding', required: false, defaultValue: 'UTF-8')
    }

    static final KeywordDefinition PARAMS = keyword('params') {
        property(name: 'values', required: false, defaultValue: [:])
    }

    static final KeywordDefinition BODY = keyword('body') {
        property(name: 'file', required: false, defaultValue: null)
        property(name: 'inline', required: false, defaultValue: null)
        property(name: 'encoding', required: false, defaultValue: 'UTF-8')
    }

    static final KeywordDefinition ARGUMENT = keyword('argument') {
        property(name: 'name', required: false, defaultValue: '')
        property(name: 'value', required: false, defaultValue: '')
    }

    static final KeywordDefinition ARGUMENTS = keyword('arguments') {
        property(name: 'values', required: false, defaultValue: [:])
    }

    static final KeywordDefinition INSERT = keyword('insert') {
        property(name: 'file', required: false, defaultValue: null)
    }

    // configs
    static final KeywordDefinition AUTHORIZATION = keyword('authorization') {
        property(name: 'url', required: false, defaultValue: '')
        property(name: 'username', required: false, defaultValue: '')
        property(name: 'password', required: false, defaultValue: '')
        property(name: 'domain', required: false, defaultValue: '')
        property(name: 'realm', required: false, defaultValue: '')
        property(name: 'mechanism', required: false, defaultValue: 'BASIC', constraints: inList(['BASIC', 'DIGEST', 'KERBEROS', 'BASIC_DIGEST']))
    }

    static final KeywordDefinition AUTHORIZATIONS = keyword('authorizations') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition CACHE = keyword('cache') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', required: false, defaultValue: false)
        property(name: 'useExpires', required: false, defaultValue: true)
        property(name: 'maxSize', required: false, defaultValue: 5000, constraints: range(0))
    }

    static final KeywordDefinition COOKIE = keyword('cookie') {
        property(name: 'secure', required: false, defaultValue: false)
        property(name: 'path', required: false, defaultValue: null)
        property(name: 'domain', required: false, defaultValue: null)
        property(name: 'name', required: false, defaultValue: '')
        property(name: 'value', required: false, defaultValue: '')
        property(name: 'domain', required: false, defaultValue: '')
        property(name: 'expires', required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition COOKIES = keyword('cookies') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', required: false, defaultValue: true)
        property(name: 'policy', required: false, defaultValue: 'standard', constraints: inList(['standard', 'compatibility', 'netscape', 'standard-strict', 'best-match', 'default', 'ignoreCookies']))
    }

    static final KeywordDefinition COUNTER = keyword('counter') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', required: false, defaultValue: false)
        property(name: 'reset', required: false, defaultValue: false)
        property(name: 'start', required: false, defaultValue: 0)
        property(name: 'end', required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'increment', required: false, defaultValue: 1)
        property(name: 'variable', required: false, defaultValue: 'c')
        property(name: 'format', required: false, defaultValue: '')
    }

    static final KeywordDefinition CSV_DATA = keyword('csv') {
        include(COMMON_PROPERTIES)
        property(name: 'ignoreFirstLine', required: false, defaultValue: false)
        property(name: 'allowQuotedData', required: false, defaultValue: false)
        property(name: 'recycle', required: false, defaultValue: true)
        property(name: 'stopUser', required: false, defaultValue: false)
        property(name: 'variables', required: true, defaultValue: [])
        property(name: 'file', required: true, defaultValue: '')
        property(name: 'encoding', required: false, defaultValue: 'UTF-8')
        property(name: 'delimiter', required: false, defaultValue: ',')
        property(name: 'shareMode', required: false, defaultValue: 'all', constraints: inList(['all', 'group', 'thread' ]))
    }

    static final KeywordDefinition DEFAULTS = keyword('defaults') {
        include(COMMON_PROPERTIES)
        property(name: 'protocol', required: false, defaultValue: 'http')
        property(name: 'domain', required: false, defaultValue: '')
        property(name: 'port', required: false, defaultValue: 80)
        property(name: 'method', required: false, defaultValue: 'GET')
        property(name: 'path', required: false, defaultValue: '')
        property(name: 'encoding', required: false, defaultValue: '')
        property(name: 'impl', required: false, defaultValue: '')
        property(name: 'connectTimeout', required: false, defaultValue: '')
        property(name: 'responseTimeout', required: false, defaultValue: '')
        property(name: 'downloadEmbeddedResources', required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', required: false, defaultValue: '')
        property(name: 'ipSource', required: false, defaultValue: '')
        property(name: 'ipSourceType', required: false, defaultValue: null, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', required: false, defaultValue: '')
        property(name: 'proxyHost', required: false, defaultValue: '')
        property(name: 'proxyPort', required: false, defaultValue: '')
        property(name: 'proxyUser', required: false, defaultValue: '')
        property(name: 'proxyPassword', required: false, defaultValue: '')
        property(name: 'saveAsMD5', required: false, defaultValue: false)
    }

    static final KeywordDefinition HEADER = keyword('header') {
        property(name: 'name', required: false, defaultValue: '')
        property(name: 'value', required: false, defaultValue: '')
    }

    static final KeywordDefinition HEADERS = keyword('headers') {
        include(COMMON_PROPERTIES)
        property(name: 'values', required: false, defaultValue: [:])
    }

    static final KeywordDefinition LOGIN = keyword('login') {
        include(COMMON_PROPERTIES)
        property(name: 'username', required: false, defaultValue: '')
        property(name: 'password', required: false, defaultValue: '')
    }

    static final KeywordDefinition RANDOM_VARIABLE = keyword('random') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', required: false, defaultValue: true)
        property(name: 'minimum', required: false, defaultValue: 0)
        property(name: 'maximum', required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'format', required: false, defaultValue: null)
        property(name: 'variable', required: false, defaultValue: 'r')
        property(name: 'seed', required: false, defaultValue: null)
    }

    static final KeywordDefinition VARIABLE = keyword('variable') {
        property(name: 'name', required: false, defaultValue: '')
        property(name: 'value', required: false, defaultValue: '')
        property(name: 'description', required: false, defaultValue: '')
    }

    static final KeywordDefinition VARIABLES = keyword('variables') {
        include(COMMON_PROPERTIES)
        property(name: 'values', required: false, defaultValue: [:])
    }

    // timers
    static final KeywordDefinition CONSTANT_TIMER = keyword('timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', required: false, defaultValue: '300', constraints: range(0))
    }

    static final KeywordDefinition UNIFORM_TIMER = keyword('uniform') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', required: false, defaultValue: '1000', constraints: range(0))
        property(name: 'range', required: false, defaultValue: '100', constraints: range(0))
    }

    static final KeywordDefinition JSR223_TIMER = keyword('jsrtimer') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    // listeners
    static final KeywordDefinition AGGREGATE = keyword('aggregate') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true, defaultValue: '')
    }

    static final KeywordDefinition BACKEND = keyword('backend')  {
        include(COMMON_PROPERTIES)
        property(name: 'classname', required: false, defaultValue: 'org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient')
        property(name: 'queueSize', required: false, defaultValue: '5000', constraints: range(1))
    }

    static final KeywordDefinition SUMMARY = keyword('summary') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true, defaultValue: '')
        property(name: 'errorsOnly', required: false, defaultValue: false)
        property(name: 'successesOnly', required: false, defaultValue: false)
        property(name: 'assertions', required: false)
        property(name: 'bytes', required: false)
        property(name: 'code', required: false)
        property(name: 'connectTime', required: false)
        property(name: 'dataType', required: false)
        property(name: 'encoding', required: false)
        property(name: 'fieldNames', required: false)
        property(name: 'fileName', required: false)
        property(name: 'hostname', required: false)
        property(name: 'idleTime', required: false)
        property(name: 'label', required: false)
        property(name: 'latency', required: false)
        property(name: 'message', required: false)
        property(name: 'requestHeaders', required: false)
        property(name: 'responseData', required: false)
        property(name: 'responseHeaders', required: false)
        property(name: 'sampleCount', required: false)
        property(name: 'samplerData', required: false)
        property(name: 'sentBytes', required: false)
        property(name: 'subresults', required: false)
        property(name: 'success', required: false)
        property(name: 'threadCounts', required: false)
        property(name: 'threadName', required: false)
        property(name: 'time', required: false)
        property(name: 'timestamp', required: false)
        property(name: 'url', required: false)
        property(name: 'xml', required: false)
    }

    static final KeywordDefinition JSR223_LISTENER = keyword('jsrlistener') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition CSS_EXTRACTOR = keyword('extract_css') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'useEmptyValue', required: false, defaultValue: false)
        property(name: 'defaultValue', required: false, defaultValue: '')
        property(name: 'match', required: false, defaultValue: 0, constraints: range(0))
        property(name: 'variable', required: true, defaultValue: '')
        property(name: 'expression', required: true, defaultValue: '')
        property(name: 'attribute', required: false, defaultValue: '')
        property(name: 'engine', required: false, defaultValue: 'JSOUP', constraints: inList(['JSOUP', 'JODD']))
    }

    static final KeywordDefinition REGEX_EXTRACTOR = keyword('extract_regex') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'field', required: false, defaultValue: 'response_body', constraints: inList(['response_body', 'response_unescaped', 'response_document', 'response_headers', 'response_code', 'response_message', 'request_headers', 'url']))
        property(name: 'useEmptyValue', required: false, defaultValue: false)
        property(name: 'defaultValue', required: false, defaultValue: '')
        property(name: 'match', required: false, defaultValue: 1, constraints: range(1))
        property(name: 'variable', required: true, defaultValue: '')
        property(name: 'expression', required: true, defaultValue: '')
        property(name: 'template', required: false, defaultValue: '\$1\$')
    }

    static final KeywordDefinition JSON_EXTRACTOR = keyword('extract_json') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValues', required: false, defaultValue: [])
        property(name: 'matches', required: false, defaultValue: [1], constraints: range(1))
        property(name: 'variables', required: true, defaultValue: [])
        property(name: 'expressions', required: true, defaultValue: [])
        property(name: 'concatenation', required: false, defaultValue: false)
    }

    static final KeywordDefinition JSR223_POSTPROCESSOR = keyword('jsrpostprocessor') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition JSR223_PREPROCESSOR = keyword('jsrpreprocessor') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    // assertions
    static final KeywordDefinition JSR223_ASSERTION = keyword('jsrassertion') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition ASSERT_RESPONSE = keyword('assert_response') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: '500all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false, defaultValue: null)
        property(name: 'field', required: false, defaultValue: 'response_data', constraints: inList(['response_data', 'response_document', 'response_code', 'response_message', 'response_headers', 'request_data', 'request_headers', 'url']))
        property(name: 'message', required: false, defaultValue: '')
        property(name: 'rule', required: false, defaultValue: 'contains', constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'ignoreStatus', required: false, defaultValue: false)
        property(name: 'any', required: false, defaultValue: false)
        property(name: 'negate', required: false, defaultValue: false)
    }

    static final KeywordDefinition ASSERT_SIZE = keyword('assert_size') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false, defaultValue: null)
        property(name: 'field', required: false, defaultValue: 'response_data', constraints: inList(['response_data', 'response_body', 'response_code', 'response_message', 'response_headers']))
        property(name: 'rule', required: false, defaultValue: 'eq', constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'size', required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_DURATION = keyword('assert_duration') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'duration', required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_XPATH = keyword('assert_xpath') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false, defaultValue: null)
        property(name: 'xpath', required: false, defaultValue: '/')
        property(name: 'ignoreWhitespace', required: false, defaultValue: false)
        property(name: 'validate', required: false, defaultValue: false)
        property(name: 'useNamespace', required: false, defaultValue: false)
        property(name: 'fetchDtd', required: false, defaultValue: false)
        property(name: 'failOnNoMatch', required: false, defaultValue: false)
        property(name: 'useTolerant', required: false, defaultValue: false)
        property(name: 'reportErrors', required: false, defaultValue: false)
        property(name: 'showWarnings', required: false, defaultValue: false)
        property(name: 'quiet', required: false, defaultValue: true)
    }

    static final KeywordDefinition ASSERT_JSON = keyword('assert_json') {
        include(COMMON_PROPERTIES)
        property(name: 'jpath', required: false, defaultValue: '$.')
        property(name: 'assertValue', required: false, defaultValue: false)
        property(name: 'assertAsRegex', required: false, defaultValue: true)
        property(name: 'value', required: false, defaultValue: '')
        property(name: 'expectNull', required: false, defaultValue: false)
        property(name: 'invert', required: false, defaultValue: false)
    }

    static final KeywordDefinition ASSERT_MD5HEX = keyword('assert_md5hex') {
        include(COMMON_PROPERTIES)
        property(name: 'value', required: false, defaultValue: '')
    }

    static final KeywordDefinition CHECK_RESPONSE = keyword('check_response') {
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_REQUEST = keyword('check_request') {
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_SIZE = keyword('check_size') {
        property(name: 'applyTo', required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final Set<String> VALID_KEYWORDS = [
            PLAN.name,
            GROUP.name,
            LOOP.name,
            SIMPLE.name,
            TRANSACTION.name,
            SECTION.name,
            INCLUDE.name,
            FOR_EACH.name,

            EXECUTE.name,

            EXECUTE_IF.name,
            EXECUTE_WHILE.name,
            EXECUTE_ONCE.name,
            EXECUTE_INTERLEAVE.name,
            EXECUTE_RANDOM.name,
            EXECUTE_ORDER.name,
            EXECUTE_PERCENT.name,
            EXECUTE_TOTAL.name,
            EXECUTE_RUNTIME.name,
            EXECUTE_SWITCH.name,

            HTTP.name,
            AJP.name,
            DEBUG.name,
            JSR223_SAMPLER.name,
            FLOW.name,

            PARAM.name,
            PARAMS.name,
            BODY.name,
            ARGUMENT.name,
            ARGUMENTS.name,
            INSERT.name,

            AUTHORIZATION.name,
            AUTHORIZATIONS.name,
            CACHE.name,
            COOKIE.name,
            COOKIES.name,
            COUNTER.name,
            CSV_DATA.name,
            DEFAULTS.name,
            HEADER.name,
            HEADERS.name,
            LOGIN.name,
            RANDOM_VARIABLE.name,
            VARIABLE.name,
            VARIABLES.name,

            CONSTANT_TIMER.name,
            UNIFORM_TIMER.name,
            JSR223_TIMER.name,

            AGGREGATE.name,
            BACKEND.name,
            SUMMARY.name,
            JSR223_LISTENER.name,

            CSS_EXTRACTOR.name,
            REGEX_EXTRACTOR.name,
            JSON_EXTRACTOR.name,

            JSR223_POSTPROCESSOR.name,
            JSR223_PREPROCESSOR.name,

            JSR223_ASSERTION.name,
            ASSERT_RESPONSE.name,
            ASSERT_SIZE.name,
            ASSERT_DURATION.name,
            ASSERT_XPATH.name,
            ASSERT_JSON.name,
            ASSERT_MD5HEX.name,
            CHECK_RESPONSE.name,
            CHECK_REQUEST.name,
            CHECK_SIZE.name,
    ].toSet().asImmutable()
}
