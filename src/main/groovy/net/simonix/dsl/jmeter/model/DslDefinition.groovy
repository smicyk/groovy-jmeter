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
        property(name: 'serialized', required: false, defalutValue: false)
        property(name: 'functionalMode', required: false, defaultValue: false)
        property(name: 'tearDownOnShutdown', required: false, defaultValue: false)
    }

    // group
    static final KeywordDefinition GROUP = keyword('group') {
        include(COMMON_PROPERTIES)
        property(name: 'users', required: false, constraints: range(1))
        property(name: 'rampUp', required: false, constraints: range(1))
        property(name: 'delayedStart', required: false)
        property(name: 'scheduler', required: false)
        property(name: 'delay', required: false, constraints: range(0))
        property(name: 'duration', required: false, constraints: range(0))
        property(name: 'loops', required: false, constraints: range(1))
        property(name: 'forever', required: false)
    }

    // controllers
    static final KeywordDefinition LOOP = keyword('loop') {
        include(COMMON_PROPERTIES)
        property(name: 'count', required: false, constraints: range(1))
        property(name: 'forever', required: false)
    }
    
    static final KeywordDefinition SIMPLE = keyword('simple') {
        include(COMMON_PROPERTIES)
    }
    
    static final KeywordDefinition TRANSACTION = keyword('transaction') {
        include(COMMON_PROPERTIES)
        property(name: 'timers', required: false)
        property(name: 'generate', required: false)
    }
    
    static final KeywordDefinition SECTION = keyword('section') {
        include(COMMON_PROPERTIES)
        property(name: 'lock', required: false)
    }
    
    static final KeywordDefinition INCLUDE = keyword('include') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true)
    }
    
    static final KeywordDefinition FOR_EACH = keyword('for_each') {
        include(COMMON_PROPERTIES)
        property(name: 'in', required: false)
        property(name: 'out', required: false)
        property(name: 'separator', required: false)
        property(name: 'start', required: false)
        property(name: 'end', required: false)
    }
    
    
    static final KeywordDefinition EXECUTE = keyword('execute') {
        include(COMMON_PROPERTIES)
        property(name: 'type', required: true, constraints: inList(['if', 'while', 'once', 'interleave', 'random', 'order', 'percent', 'total', 'runtime', 'switch']))
    }

    static final KeywordDefinition EXECUTE_IF = keyword('execute_if') {
        include(COMMON_PROPERTIES)
        property(name: 'condition', required: true)
        property(name: 'useExpression', required: false)
        property(name: 'evaluateAll', required: false)
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
        property(name: 'ignore', required: false)
        property(name: 'acrossUsers', required: false)
    }

    static final KeywordDefinition EXECUTE_RANDOM = keyword('execute_random') {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', required: false)
    }

    static final KeywordDefinition EXECUTE_ORDER = keyword('execute_order') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_PERCENT = keyword('execute_percent') {
        include(COMMON_PROPERTIES)
        property(name: 'percent', required: false, constraints: range(0, 100))
        property(name: 'perUser', required: false)
    }

    static final KeywordDefinition EXECUTE_TOTAL = keyword('execute_total') {
        include(COMMON_PROPERTIES)
        property(name: 'total', required: false, constraints: range(0))
        property(name: 'perUser', required: false)
    }

    static final KeywordDefinition EXECUTE_RUNTIME = keyword('execute_runtime') {
        include(COMMON_PROPERTIES)
        property(name: 'runtime', required: false, constraints: range(0))
    }

    static final KeywordDefinition EXECUTE_SWITCH = keyword('execute_switch') {
        include(COMMON_PROPERTIES)
        property(name: 'value', required: false)
    }

    // samplers
    static final Set<PropertyDefinition> JSR223_PROPERTIES = properties {
        property(name: 'inline', required: false)
        property(name: 'cacheKey', required: false)
        property(name: 'file', required: false)
        property(name: 'parameters', required: false)
        property(name: 'language', required: false, constraints: inList(['groovy']))
    }

    static final Set<PropertyDefinition> HTTP_COMMON_PROPERTIES = properties {
        property(name: 'method', required: false)
        property(name: 'protocol', required: false)
        property(name: 'domain', required: false)
        property(name: 'port', required: false, constraints: range(1, 65535))
        property(name: 'path', required: false)
        property(name: 'encoding', required: false)
        property(name: 'autoRedirects', required: false)
        property(name: 'followRedirects', required: false)
        property(name: 'keepAlive', required: false)
        property(name: 'multipart', required: false)
        property(name: 'browserCompatibleMultipart', required: false)
    }

    static final KeywordDefinition HTTP = keyword('http') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'impl', required: false)
        property(name: 'connectTimeout', required: false)
        property(name: 'responseTimeout', required: false)
        property(name: 'downloadEmbeddedResources', required: false)
        property(name: 'embeddedConcurrent', required: false)
        property(name: 'embeddedConcurrentDownloads', required: false)
        property(name: 'embeddedResourceUrl', required: false)
        property(name: 'ipSource', required: false)
        property(name: 'ipSourceType', required: false, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', required: false)
        property(name: 'proxyHost', required: false)
        property(name: 'proxyPort', required: false)
        property(name: 'proxyUser', required: false)
        property(name: 'proxyPassword', required: false)
        property(name: 'saveAsMD5', required: false)
    }

    static final KeywordDefinition AJP = keyword('ajp') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'downloadEmbeddedResources', required: false)
        property(name: 'embeddedConcurrent', required: false)
        property(name: 'embeddedConcurrentDownloads', required: false)
        property(name: 'embeddedResourceUrl', required: false)
        property(name: 'saveAsMD5', required: false)
    }

    static final KeywordDefinition DEBUG = keyword('debug') {
        include(COMMON_PROPERTIES)
        property(name: 'displayJMeterProperties', required: false)
        property(name: 'displayJMeterVariables', required: false)
        property(name: 'displaySystemProperties', required: false)
    }

    static final KeywordDefinition JSR223_SAMPLER = keyword('jsrsampler') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition FLOW = keyword('flow') {
        include(COMMON_PROPERTIES)
        property(name: 'action', required: false, constraints: inList(['pause', 'stop', 'stop_now', 'restart_next_loop', 'start_next', 'break']))
        property(name: 'target', required: false, constraints: inList(['current', 'all']))
        property(name: 'duration', required: false, constraints: range(0))
    }

    // common
    static final KeywordDefinition PARAM = keyword('param') {
        property(name: 'name', required: false)
        property(name: 'value', required: false)
        property(name: 'encoded', required: false)
        property(name: 'encoding', required: false)
    }

    static final KeywordDefinition PARAMS = keyword('params') {
        property(name: 'values', required: false)
    }

    static final KeywordDefinition BODY = keyword('body') {
        property(name: 'file', required: false)
        property(name: 'inline', required: false)
        property(name: 'encoding', required: false)
    }

    static final KeywordDefinition ARGUMENT = keyword('argument') {
        property(name: 'name', required: false)
        property(name: 'value', required: false)
    }

    static final KeywordDefinition ARGUMENTS = keyword('arguments') {
        property(name: 'values', required: false)
    }

    static final KeywordDefinition INSERT = keyword('insert') {
        property(name: 'file', required: false)
    }

    // configs
    static final KeywordDefinition AUTHORIZATION = keyword('authorization') {
        property(name: 'url', required: false)
        property(name: 'username', required: false)
        property(name: 'password', required: false)
        property(name: 'domain', required: false)
        property(name: 'realm', required: false)
        property(name: 'mechanism', required: false, constraints: inList(['BASIC', 'DIGEST', 'KERBEROS', 'BASIC_DIGEST']))
    }

    static final KeywordDefinition AUTHORIZATIONS = keyword('authorizations') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition CACHE = keyword('cache') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', required: false)
        property(name: 'useExpires', required: false)
        property(name: 'maxSize', required: false, constraints: range(0))
    }

    static final KeywordDefinition COOKIE = keyword('cookie') {
        property(name: 'secure', required: false)
        property(name: 'path', required: false)
        property(name: 'domain', required: false)
        property(name: 'name', required: false)
        property(name: 'value', required: false)
        property(name: 'domain', required: false)
        property(name: 'expires', required: false, constraints: range(0))
    }

    static final KeywordDefinition COOKIES = keyword('cookies') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', required: false)
        property(name: 'policy', required: false, constraints: inList(['standard', 'compatibility', 'netscape', 'standard-strict', 'best-match', 'default', 'ignoreCookies']))
    }

    static final KeywordDefinition COUNTER = keyword('counter') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', required: false)
        property(name: 'reset', required: false)
        property(name: 'start', required: false)
        property(name: 'end', required: false)
        property(name: 'increment', required: false)
        property(name: 'variable', required: false)
        property(name: 'format', required: false)
    }

    static final KeywordDefinition CSV_DATA = keyword('csv') {
        include(COMMON_PROPERTIES)
        property(name: 'ignoreFirstLine', required: false)
        property(name: 'allowQuotedData', required: false)
        property(name: 'recycle', required: false)
        property(name: 'stopUser', required: false)
        property(name: 'variables', required: true)
        property(name: 'file', required: true)
        property(name: 'encoding', required: false)
        property(name: 'delimiter', required: false)
        property(name: 'shareMode', required: false, constraints: inList(['all', 'group', 'thread' ]))
    }

    static final KeywordDefinition DEFAULTS = keyword('defaults') {
        include(COMMON_PROPERTIES)
        property(name: 'protocol', required: false)
        property(name: 'domain', required: false)
        property(name: 'port', required: false)
        property(name: 'method', required: false)
        property(name: 'path', required: false)
        property(name: 'encoding', required: false)
        property(name: 'impl', required: false)
        property(name: 'connectTimeout', required: false)
        property(name: 'responseTimeout', required: false)
        property(name: 'downloadEmbeddedResources', required: false)
        property(name: 'embeddedConcurrent', required: false)
        property(name: 'embeddedConcurrentDownloads', required: false)
        property(name: 'embeddedResourceUrl', required: false)
        property(name: 'ipSource', required: false)
        property(name: 'ipSourceType', required: false, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', required: false)
        property(name: 'proxyHost', required: false)
        property(name: 'proxyPort', required: false)
        property(name: 'proxyUser', required: false)
        property(name: 'proxyPassword', required: false)
        property(name: 'saveAsMD5', required: false)
    }

    static final KeywordDefinition HEADER = keyword('header') {
        property(name: 'name', required: false)
        property(name: 'value', required: false)
    }

    static final KeywordDefinition HEADERS = keyword('headers') {
        include(COMMON_PROPERTIES)
        property(name: 'values', required: false)
    }

    static final KeywordDefinition LOGIN = keyword('login') {
        include(COMMON_PROPERTIES)
        property(name: 'username', required: false)
        property(name: 'password', required: false)
    }

    static final KeywordDefinition RANDOM_VARIABLE = keyword('random') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', required: false)
        property(name: 'minimum', required: false)
        property(name: 'maximum', required: false)
        property(name: 'format', required: false)
        property(name: 'variable', required: false)
        property(name: 'seed', required: false)
    }

    static final KeywordDefinition VARIABLE = keyword('variable') {
        property(name: 'name', required: false)
        property(name: 'value', required: false)
        property(name: 'description', required: false)
    }

    static final KeywordDefinition VARIABLES = keyword('variables') {
        include(COMMON_PROPERTIES)
        property(name: 'values', required: false)
    }

    // timers
    static final KeywordDefinition CONSTANT_TIMER = keyword('timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', required: false, constraints: range(0))
    }

    static final KeywordDefinition UNIFORM_TIMER = keyword('uniform') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', required: false, constraints: range(0))
        property(name: 'range', required: false, constraints: range(0))
    }

    static final KeywordDefinition JSR223_TIMER = keyword('jsrtimer') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    // listeners
    static final KeywordDefinition AGGREGATE = keyword('aggregate') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true)
    }

    static final KeywordDefinition BACKEND = keyword('backend')  {
        include(COMMON_PROPERTIES)
        property(name: 'classname', required: false)
        property(name: 'queueSize', required: false, constraints: range(1))
    }

    static final KeywordDefinition SUMMARY = keyword('summary') {
        include(COMMON_PROPERTIES)
        property(name: 'file', required: true)
        property(name: 'errorsOnly', required: false)
        property(name: 'successesOnly', required: false)
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
        property(name: 'applyTo', required: false, constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'useEmptyValue', required: false)
        property(name: 'defaultValue', required: false)
        property(name: 'match', required: false, constraints: range(0))
        property(name: 'variable', required: true)
        property(name: 'expression', required: true)
        property(name: 'attribute', required: false)
        property(name: 'engine', required: false, constraints: inList(['JSOUP', 'JODD']))
    }

    static final KeywordDefinition REGEX_EXTRACTOR = keyword('extract_regex') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'field', required: false, constraints: inList(['response_body', 'response_unescaped', 'response_document', 'response_headers', 'response_code', 'response_message', 'request_headers', 'url']))
        property(name: 'useEmptyValue', required: false)
        property(name: 'defaultValue', required: false)
        property(name: 'match', required: false, constraints: range(1))
        property(name: 'variable', required: true)
        property(name: 'expression', required: true)
        property(name: 'template', required: false)
    }

    static final KeywordDefinition JSON_EXTRACTOR = keyword('extract_json') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValues', required: false)
        property(name: 'matches', required: false, constraints: range(1))
        property(name: 'variables', required: true)
        property(name: 'expressions', required: true)
        property(name: 'concatenation', required: false)
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
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false)
        property(name: 'field', required: false, constraints: inList(['response_data', 'response_document', 'response_code', 'response_message', 'response_headers', 'request_data', 'request_headers', 'url']))
        property(name: 'message', required: false)
        property(name: 'rule', required: false, constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'ignoreStatus', required: false)
        property(name: 'any', required: false)
        property(name: 'negate', required: false)
    }

    static final KeywordDefinition ASSERT_SIZE = keyword('assert_size') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false)
        property(name: 'field', required: false, constraints: inList(['response_data', 'response_body', 'response_code', 'response_message', 'response_headers']))
        property(name: 'rule', required: false, constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'size', required: false, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_DURATION = keyword('assert_duration') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'duration', required: false, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_XPATH = keyword('assert_xpath') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', required: false)
        property(name: 'xpath', required: false)
        property(name: 'ignoreWhitespace', required: false)
        property(name: 'validate', required: false)
        property(name: 'useNamespace', required: false)
        property(name: 'fetchDtd', required: false)
        property(name: 'failOnNoMatch', required: false)
        property(name: 'useTolerant', required: false)
        property(name: 'reportErrors', required: false)
        property(name: 'showWarnings', required: false)
        property(name: 'quiet', required: false)
    }

    static final KeywordDefinition ASSERT_JSON = keyword('assert_json') {
        include(COMMON_PROPERTIES)
        property(name: 'jpath', required: false)
        property(name: 'assertValue', required: false)
        property(name: 'assertAsRegex', required: false)
        property(name: 'value', required: false)
        property(name: 'expectNull', required: false)
        property(name: 'invert', required: false)
    }

    static final KeywordDefinition ASSERT_MD5HEX = keyword('assert_md5hex') {
        include(COMMON_PROPERTIES)
        property(name: 'value', required: false)
    }

    static final KeywordDefinition CHECK_RESPONSE = keyword('check_response') {
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_REQUEST = keyword('check_request') {
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_SIZE = keyword('check_size') {
        property(name: 'applyTo', required: false, constraints: inList(['all', 'parent', 'children', 'variable']))
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
