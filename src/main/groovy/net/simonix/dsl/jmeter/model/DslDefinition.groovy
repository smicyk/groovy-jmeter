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

import groovy.transform.CompileStatic

@CompileStatic
final class DslDefinition {
    static final KeywordDefinition PLAN = new KeywordDefinition(name: 'plan')
    static final KeywordDefinition GROUP = new KeywordDefinition(name: 'group')

    static final KeywordDefinition LOOP = new KeywordDefinition(name: 'loop')
    static final KeywordDefinition SIMPLE = new KeywordDefinition(name: 'simple')
    static final KeywordDefinition TRANSACTION = new KeywordDefinition(name: 'transaction')
    static final KeywordDefinition SECTION = new KeywordDefinition(name: 'section')
    static final KeywordDefinition INCLUDE = new KeywordDefinition(name: 'include')
    static final KeywordDefinition FOR_EACH = new KeywordDefinition(name: 'for_each')

    static final KeywordDefinition EXECUTE = new KeywordDefinition(name: 'execute')

    static final KeywordDefinition EXECUTE_IF = new KeywordDefinition(name: 'execute_if')
    static final KeywordDefinition EXECUTE_WHILE = new KeywordDefinition(name: 'execute_while')
    static final KeywordDefinition EXECUTE_ONCE = new KeywordDefinition(name: 'execute_once')
    static final KeywordDefinition EXECUTE_INTERLEAVE = new KeywordDefinition(name: 'execute_interleave')
    static final KeywordDefinition EXECUTE_RANDOM = new KeywordDefinition(name: 'execute_random')
    static final KeywordDefinition EXECUTE_ORDER = new KeywordDefinition(name: 'execute_order')
    static final KeywordDefinition EXECUTE_PERCENT = new KeywordDefinition(name: 'execute_percent')
    static final KeywordDefinition EXECUTE_TOTAL = new KeywordDefinition(name: 'execute_total')
    static final KeywordDefinition EXECUTE_RUNTIME = new KeywordDefinition(name: 'execute_runtime')
    static final KeywordDefinition EXECUTE_SWITCH = new KeywordDefinition(name: 'execute_switch')

    static final KeywordDefinition EXEC_IF = new KeywordDefinition(name: 'exec_if')
    static final KeywordDefinition EXEC_WHILE = new KeywordDefinition(name: 'exec_while')
    static final KeywordDefinition EXEC_ONCE = new KeywordDefinition(name: 'exec_once')
    static final KeywordDefinition EXEC_INTERLEAVE = new KeywordDefinition(name: 'exec_interleave')
    static final KeywordDefinition EXEC_RANDOM = new KeywordDefinition(name: 'exec_random')
    static final KeywordDefinition EXEC_ORDER = new KeywordDefinition(name: 'exec_order')
    static final KeywordDefinition EXEC_PERCENT = new KeywordDefinition(name: 'exec_percent')
    static final KeywordDefinition EXEC_TOTAL = new KeywordDefinition(name: 'exec_total')
    static final KeywordDefinition EXEC_RUNTIME = new KeywordDefinition(name: 'exec_runtime')
    static final KeywordDefinition EXEC_SWITCH = new KeywordDefinition(name: 'exec_switch')

    static final KeywordDefinition HTTP = new KeywordDefinition(name: 'http')
    static final KeywordDefinition AJP = new KeywordDefinition(name: 'ajp')
    static final KeywordDefinition DEBUG = new KeywordDefinition(name: 'debug')
    static final KeywordDefinition JSR223_SAMPLER = new KeywordDefinition(name: 'jsrsampler')
    static final KeywordDefinition FLOW = new KeywordDefinition(name: 'flow')

    static final KeywordDefinition PARAM = new KeywordDefinition(name: 'param')
    static final KeywordDefinition PARAMS = new KeywordDefinition(name: 'params')
    static final KeywordDefinition BODY = new KeywordDefinition(name: 'body')
    static final KeywordDefinition ARGUMENT = new KeywordDefinition(name: 'argument')
    static final KeywordDefinition ARGUMENTS = new KeywordDefinition(name: 'arguments')
    static final KeywordDefinition INSERT = new KeywordDefinition(name: 'insert')

    static final KeywordDefinition AUTHORIZATION = new KeywordDefinition(name: 'authorization')
    static final KeywordDefinition AUTHORIZATIONS = new KeywordDefinition(name: 'authorizations')
    static final KeywordDefinition CACHE = new KeywordDefinition(name: 'cache')
    static final KeywordDefinition COOKIE = new KeywordDefinition(name: 'cookie')
    static final KeywordDefinition COOKIES = new KeywordDefinition(name: 'cookies')
    static final KeywordDefinition COUNTER = new KeywordDefinition(name: 'counter')
    static final KeywordDefinition CSV_DATA = new KeywordDefinition(name: 'csv')
    static final KeywordDefinition DEFAULTS = new KeywordDefinition(name: 'defaults')
    static final KeywordDefinition HEADER = new KeywordDefinition(name: 'header')
    static final KeywordDefinition HEADERS = new KeywordDefinition(name: 'headers')
    static final KeywordDefinition LOGIN = new KeywordDefinition(name: 'login')
    static final KeywordDefinition RANDOM_VARIABLE = new KeywordDefinition(name: 'random')
    static final KeywordDefinition VARIABLE = new KeywordDefinition(name: 'variable')
    static final KeywordDefinition VARIABLES = new KeywordDefinition(name: 'variables')

    static final KeywordDefinition CONSTANT_TIMER = new KeywordDefinition(name: 'timer')
    static final KeywordDefinition UNIFORM_TIMER = new KeywordDefinition(name: 'uniform')
    static final KeywordDefinition JSR223_TIMER = new KeywordDefinition(name: 'jsrtimer')

    static final KeywordDefinition AGGREGATE = new KeywordDefinition(name: 'aggregate')
    static final KeywordDefinition BACKEND = new KeywordDefinition(name: 'backend')
    static final KeywordDefinition SUMMARY = new KeywordDefinition(name: 'summary')
    static final KeywordDefinition JSR223_LISTENER = new KeywordDefinition(name: 'jsrlistener')

    static final KeywordDefinition CSS_EXTRACTOR = new KeywordDefinition(name: 'extract_css')
    static final KeywordDefinition REGEX_EXTRACTOR = new KeywordDefinition(name: 'extract_regex')
    static final KeywordDefinition JSON_EXTRACTOR = new KeywordDefinition(name: 'extract_json')

    static final KeywordDefinition JSR223_POSTPROCESSOR = new KeywordDefinition(name: 'jsrpostprocessor')
    static final KeywordDefinition JSR223_PREPROCESSOR = new KeywordDefinition(name: 'jsrpreprocessor')

    static final KeywordDefinition JSR223_ASSERTION = new KeywordDefinition(name: 'jsrassertion')
    static final KeywordDefinition ASSERT_RESPONSE = new KeywordDefinition(name: 'assert_response')
    static final KeywordDefinition ASSERT_SIZE = new KeywordDefinition(name: 'assert_size')
    static final KeywordDefinition ASSERT_DURATION = new KeywordDefinition(name: 'assert_duration')
    static final KeywordDefinition ASSERT_XPATH = new KeywordDefinition(name: 'assert_xpath')
    static final KeywordDefinition ASSERT_JSON = new KeywordDefinition(name: 'assert_json')
    static final KeywordDefinition ASSERT_MD5HEX = new KeywordDefinition(name: 'assert_md5hex')
    static final KeywordDefinition CHECK_RESPONSE = new KeywordDefinition(name: 'check_response')
    static final KeywordDefinition CHECK_REQUEST = new KeywordDefinition(name: 'check_request')
    static final KeywordDefinition CHECK_SIZE = new KeywordDefinition(name: 'check_size')

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

            EXEC_IF.name,
            EXEC_WHILE.name,
            EXEC_ONCE.name,
            EXEC_INTERLEAVE.name,
            EXEC_RANDOM.name,
            EXEC_ORDER.name,
            EXEC_PERCENT.name,
            EXEC_TOTAL.name,
            EXEC_RUNTIME.name,
            EXEC_SWITCH.name,

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

    static final Set<PropertyDefinition> COMMON_PROPERTIES = [
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'comments', required: false),
            new PropertyDefinition(name: 'enabled', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> PLAN_PROPERTIES = [
            new PropertyDefinition(name: 'serialized', required: false),
            new PropertyDefinition(name: 'functionalMode', required: false),
            new PropertyDefinition(name: 'tearDownOnShutdown', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> GROUP_PROPERTIES = [
            new PropertyDefinition(name: 'users', required: false),
            new PropertyDefinition(name: 'rampUp', required: false),
            new PropertyDefinition(name: 'delayedStart', required: false),
            new PropertyDefinition(name: 'scheduler', required: false),
            new PropertyDefinition(name: 'delay', required: false),
            new PropertyDefinition(name: 'duration', required: false),
            new PropertyDefinition(name: 'loops', required: false),
            new PropertyDefinition(name: 'forever', required: false),
    ].toSet().asImmutable()

    // controllers
    static final Set<PropertyDefinition> LOOP_PROPERTIES = [
            new PropertyDefinition(name: 'count', required: false),
            new PropertyDefinition(name: 'forever', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> SIMPLE_PROPERTIES = [].toSet().asImmutable()

    static final Set<PropertyDefinition> TRANSACTION_PROPERTIES = [
            new PropertyDefinition(name: 'timers', required: false),
            new PropertyDefinition(name: 'generate', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> SECTION_PROPERTIES = [
            new PropertyDefinition(name: 'lock', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> INCLUDE_PROPERTIES = [
            new PropertyDefinition(name: 'file', required: true),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> FOR_EACH_PROPERTIES = [
            new PropertyDefinition(name: 'in', required: false),
            new PropertyDefinition(name: 'out', required: false),
            new PropertyDefinition(name: 'separator', required: false),
            new PropertyDefinition(name: 'start', required: false),
            new PropertyDefinition(name: 'end', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_PROPERTIES = [
            new PropertyDefinition(name: 'type', required: true),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_IF_PROPERTIES = [
            new PropertyDefinition(name: 'condition', required: true),
            new PropertyDefinition(name: 'useExpression', required: false),
            new PropertyDefinition(name: 'evaluateAll', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_WHILE_PROPERTIES = [
            new PropertyDefinition(name: 'condition', required: true),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_ONCE_PROPERTIES = [].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_INTERLEAVE_PROPERTIES = [
            new PropertyDefinition(name: 'ignore', required: false),
            new PropertyDefinition(name: 'acrossUsers', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_RANDOM_PROPERTIES = [
            new PropertyDefinition(name: 'ignore', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_ORDER_PROPERTIES = [].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_PERCENT_PROPERTIES = [
            new PropertyDefinition(name: 'percent', required: false),
            new PropertyDefinition(name: 'perUser', required: false),
            new PropertyDefinition(name: 'maxThroughput', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_TOTAL_PROPERTIES = [
            new PropertyDefinition(name: 'perUser', required: false),
            new PropertyDefinition(name: 'total', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_RUNTIME_PROPERTIES = [
            new PropertyDefinition(name: 'runtime', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> EXECUTE_SWITCH_PROPERTIES = [
            new PropertyDefinition(name: 'value', required: false),
    ].toSet().asImmutable()

    // samplers
    static final Set<PropertyDefinition> HTTP_COMMON_PROPERTIES = [
            new PropertyDefinition(name: 'method', required: false),
            new PropertyDefinition(name: 'protocol', required: false),
            new PropertyDefinition(name: 'domain', required: false),
            new PropertyDefinition(name: 'port', required: false),
            new PropertyDefinition(name: 'path', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
            new PropertyDefinition(name: 'autoRedirects', required: false),
            new PropertyDefinition(name: 'followRedirects', required: false),
            new PropertyDefinition(name: 'keepAlive', required: false),
            new PropertyDefinition(name: 'multipart', required: false),
            new PropertyDefinition(name: 'browserCompatibleMultipart', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> HTTP_PROPERTIES = [
            new PropertyDefinition(name: 'impl', required: false),
            new PropertyDefinition(name: 'connectTimeout', required: false),
            new PropertyDefinition(name: 'responseTimeout', required: false),
            new PropertyDefinition(name: 'downloadEmbeddedResources', required: false),
            new PropertyDefinition(name: 'embeddedConcurrent', required: false),
            new PropertyDefinition(name: 'embeddedConcurrentDownloads', required: false),
            new PropertyDefinition(name: 'embeddedResourceUrl', required: false),
            new PropertyDefinition(name: 'ipSource', required: false),
            new PropertyDefinition(name: 'ipSourceType', required: false),
            new PropertyDefinition(name: 'proxySchema', required: false),
            new PropertyDefinition(name: 'proxyHost', required: false),
            new PropertyDefinition(name: 'proxyPort', required: false),
            new PropertyDefinition(name: 'proxyUser', required: false),
            new PropertyDefinition(name: 'proxyPassword', required: false),
            new PropertyDefinition(name: 'saveAsMD5', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> AJP_PROPERTIES = [
            new PropertyDefinition(name: 'downloadEmbeddedResources', required: false),
            new PropertyDefinition(name: 'embeddedConcurrent', required: false),
            new PropertyDefinition(name: 'embeddedConcurrentDownloads', required: false),
            new PropertyDefinition(name: 'embeddedResourceUrl', required: false),
            new PropertyDefinition(name: 'saveAsMD5', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> FLOW_CONTROL_ACTION_PROPERTIES = [
            new PropertyDefinition(name: 'action', required: false),
            new PropertyDefinition(name: 'target', required: false),
            new PropertyDefinition(name: 'duration', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> DEBUG_PROPERTIES = [
            new PropertyDefinition(name: 'displayJMeterProperties', required: false),
            new PropertyDefinition(name: 'displayJMeterVariables', required: false),
            new PropertyDefinition(name: 'displaySystemProperties', required: false),
    ].toSet().asImmutable()

    // common
    static final Set<PropertyDefinition> ARGUMENT_PROPERTIES = [
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'value', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ARGUMENTS_PROPERTIES = [
            new PropertyDefinition(name: 'values', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> BODY_PROPERTIES = [
            new PropertyDefinition(name: 'file', required: false),
            new PropertyDefinition(name: 'inline', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> INSERT_PROPERTIES = [
            new PropertyDefinition(name: 'file', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> PARAM_PROPERTIES = [
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'value', required: false),
            new PropertyDefinition(name: 'encoded', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> PARAMS_PROPERTIES = [
            new PropertyDefinition(name: 'values', required: false),
    ].toSet().asImmutable()

    // configs
    static final Set<PropertyDefinition> AUTHORIZATION_PROPERTIES = [
            new PropertyDefinition(name: 'url', required: false),
            new PropertyDefinition(name: 'username', required: false),
            new PropertyDefinition(name: 'password', required: false),
            new PropertyDefinition(name: 'domain', required: false),
            new PropertyDefinition(name: 'realm', required: false),
            new PropertyDefinition(name: 'mechanism', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> AUTHORIZATIONS_PROPERTIES = [].toSet().asImmutable()

    static final Set<PropertyDefinition> DEFAULTS_PROPERTIES = [
            new PropertyDefinition(name: 'protocol', required: false),
            new PropertyDefinition(name: 'domain', required: false),
            new PropertyDefinition(name: 'port', required: false),
            new PropertyDefinition(name: 'method', required: false),
            new PropertyDefinition(name: 'path', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
            new PropertyDefinition(name: 'impl', required: false),
            new PropertyDefinition(name: 'connectTimeout', required: false),
            new PropertyDefinition(name: 'responseTimeout', required: false),
            new PropertyDefinition(name: 'downloadEmbeddedResources', required: false),
            new PropertyDefinition(name: 'embeddedConcurrent', required: false),
            new PropertyDefinition(name: 'embeddedConcurrentDownloads', required: false),
            new PropertyDefinition(name: 'embeddedResourceUrl', required: false),
            new PropertyDefinition(name: 'ipSource', required: false),
            new PropertyDefinition(name: 'ipSourceType', required: false),
            new PropertyDefinition(name: 'proxySchema', required: false),
            new PropertyDefinition(name: 'proxyHost', required: false),
            new PropertyDefinition(name: 'proxyPort', required: false),
            new PropertyDefinition(name: 'proxyUser', required: false),
            new PropertyDefinition(name: 'proxyPassword', required: false),
            new PropertyDefinition(name: 'saveAsMD5', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CACHE_PROPERTIES = [
            new PropertyDefinition(name: 'clearEachIteration', required: false),
            new PropertyDefinition(name: 'useExpires', required: false),
            new PropertyDefinition(name: 'maxSize', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> COOKIE_PROPERTIES = [
            new PropertyDefinition(name: 'secure', required: false),
            new PropertyDefinition(name: 'path', required: false),
            new PropertyDefinition(name: 'domain', required: false),
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'value', required: false),
            new PropertyDefinition(name: 'domain', required: false),
            new PropertyDefinition(name: 'expires', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> COOKIES_PROPERTIES = [
            new PropertyDefinition(name: 'clearEachIteration', required: false),
            new PropertyDefinition(name: 'policy', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> COUNTER_PROPERTIES = [
            new PropertyDefinition(name: 'perUser', required: false),
            new PropertyDefinition(name: 'reset', required: false),
            new PropertyDefinition(name: 'start', required: false),
            new PropertyDefinition(name: 'end', required: false),
            new PropertyDefinition(name: 'increment', required: false),
            new PropertyDefinition(name: 'variable', required: false),
            new PropertyDefinition(name: 'format', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CSV_DATA_PROPERTIES = [
            new PropertyDefinition(name: 'ignoreFirstLine', required: false),
            new PropertyDefinition(name: 'allowQuotedData', required: false),
            new PropertyDefinition(name: 'recycle', required: false),
            new PropertyDefinition(name: 'stopUser', required: false),
            new PropertyDefinition(name: 'variables', required: true),
            new PropertyDefinition(name: 'file', required: true),
            new PropertyDefinition(name: 'encoding', required: false),
            new PropertyDefinition(name: 'delimiter', required: false),
            new PropertyDefinition(name: 'shareMode', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> LOGIN_PROPERTIES = [
            new PropertyDefinition(name: 'username', required: false),
            new PropertyDefinition(name: 'password', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> RANDOM_VARIABLE_PROPERTIES = [
            new PropertyDefinition(name: 'perUser', required: false),
            new PropertyDefinition(name: 'minimum', required: false),
            new PropertyDefinition(name: 'maximum', required: false),
            new PropertyDefinition(name: 'format', required: false),
            new PropertyDefinition(name: 'variable', required: false),
            new PropertyDefinition(name: 'seed', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> HEADER_PROPERTIES = [
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'value', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> HEADERS_PROPERTIES = [
            new PropertyDefinition(name: 'values', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> VARIABLE_PROPERTIES = [
            new PropertyDefinition(name: 'name', required: false),
            new PropertyDefinition(name: 'value', required: false),
            new PropertyDefinition(name: 'description', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> VARIABLES_PROPERTIES = [
            new PropertyDefinition(name: 'values', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> JSR223_PROPERTIES = [
            new PropertyDefinition(name: 'inline', required: false),
            new PropertyDefinition(name: 'cacheKey', required: false),
            new PropertyDefinition(name: 'file', required: false),
            new PropertyDefinition(name: 'parameters', required: false),
            new PropertyDefinition(name: 'language', required: false),
    ].toSet().asImmutable()

    // timers
    static final Set<PropertyDefinition> CONSTANT_TIMER_PROPERTIES = [
            new PropertyDefinition(name: 'delay', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> UNIFORM_TIMER_PROPERTIES = [
            new PropertyDefinition(name: 'delay', required: false),
            new PropertyDefinition(name: 'range', required: false),
    ].toSet().asImmutable()

    // listeners
    static final Set<PropertyDefinition> AGGREGATE_PROPERTIES = [
            new PropertyDefinition(name: 'file', required: true),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> BACKEND_PROPERTIES = [
            new PropertyDefinition(name: 'classname', required: false),
            new PropertyDefinition(name: 'queueSize', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> SUMMARY_PROPERTIES = [
            new PropertyDefinition(name: 'file', required: true),
            new PropertyDefinition(name: 'errorsOnly', required: false),
            new PropertyDefinition(name: 'successesOnly', required: false),
            new PropertyDefinition(name: 'assertions', required: false),
            new PropertyDefinition(name: 'bytes', required: false),
            new PropertyDefinition(name: 'code', required: false),
            new PropertyDefinition(name: 'connectTime', required: false),
            new PropertyDefinition(name: 'dataType', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
            new PropertyDefinition(name: 'fieldNames', required: false),
            new PropertyDefinition(name: 'fileName', required: false),
            new PropertyDefinition(name: 'hostname', required: false),
            new PropertyDefinition(name: 'idleTime', required: false),
            new PropertyDefinition(name: 'label', required: false),
            new PropertyDefinition(name: 'latency', required: false),
            new PropertyDefinition(name: 'message', required: false),
            new PropertyDefinition(name: 'requestHeaders', required: false),
            new PropertyDefinition(name: 'responseData', required: false),
            new PropertyDefinition(name: 'responseHeaders', required: false),
            new PropertyDefinition(name: 'sampleCount', required: false),
            new PropertyDefinition(name: 'samplerData', required: false),
            new PropertyDefinition(name: 'sentBytes', required: false),
            new PropertyDefinition(name: 'subresults', required: false),
            new PropertyDefinition(name: 'success', required: false),
            new PropertyDefinition(name: 'threadCounts', required: false),
            new PropertyDefinition(name: 'threadName', required: false),
            new PropertyDefinition(name: 'time', required: false),
            new PropertyDefinition(name: 'timestamp', required: false),
            new PropertyDefinition(name: 'url', required: false),
            new PropertyDefinition(name: 'xml', required: false),
    ].toSet().asImmutable()

    // extractors
    static final Set<PropertyDefinition> CSS_EXTRACTOR_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'useEmptyValue', required: false),
            new PropertyDefinition(name: 'defaultValue', required: false),
            new PropertyDefinition(name: 'match', required: false),
            new PropertyDefinition(name: 'variable', required: true),
            new PropertyDefinition(name: 'expression', required: true),
            new PropertyDefinition(name: 'attribute', required: false),
            new PropertyDefinition(name: 'engine', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> JSON_EXTRACTOR_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'defaultValues', required: false),
            new PropertyDefinition(name: 'matches', required: false),
            new PropertyDefinition(name: 'variables', required: true),
            new PropertyDefinition(name: 'expressions', required: true),
            new PropertyDefinition(name: 'concatenation', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> REGEX_EXTRACTOR_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'field', required: false),
            new PropertyDefinition(name: 'useEmptyValue', required: false),
            new PropertyDefinition(name: 'defaultValue', required: false),
            new PropertyDefinition(name: 'match', required: false),
            new PropertyDefinition(name: 'variable', required: true),
            new PropertyDefinition(name: 'expression', required: true),
            new PropertyDefinition(name: 'template', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_RESPONSE_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'variable', required: false),
            new PropertyDefinition(name: 'field', required: false),
            new PropertyDefinition(name: 'message', required: false),
            new PropertyDefinition(name: 'rule', required: false),
            new PropertyDefinition(name: 'ignoreStatus', required: false),
            new PropertyDefinition(name: 'any', required: false),
            new PropertyDefinition(name: 'negate', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_SIZE_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'variable', required: false),
            new PropertyDefinition(name: 'field', required: false),
            new PropertyDefinition(name: 'rule', required: false),
            new PropertyDefinition(name: 'size', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_DURATION_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'duration', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_XPATH_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
            new PropertyDefinition(name: 'variable', required: false),
            new PropertyDefinition(name: 'xpath', required: false),
            new PropertyDefinition(name: 'ignoreWhitespace', required: false),
            new PropertyDefinition(name: 'validate', required: false),
            new PropertyDefinition(name: 'useNamespace', required: false),
            new PropertyDefinition(name: 'fetchDtd', required: false),
            new PropertyDefinition(name: 'failOnNoMatch', required: false),
            new PropertyDefinition(name: 'useTolerant', required: false),
            new PropertyDefinition(name: 'reportErrors', required: false),
            new PropertyDefinition(name: 'showWarnings', required: false),
            new PropertyDefinition(name: 'quiet', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_JSON_PROPERTIES = [
            new PropertyDefinition(name: 'jpath', required: false),
            new PropertyDefinition(name: 'assert_value', required: false),
            new PropertyDefinition(name: 'assert_as_regex', required: false),
            new PropertyDefinition(name: 'value', required: false),
            new PropertyDefinition(name: 'expectNull', required: false),
            new PropertyDefinition(name: 'invert', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> ASSERT_MD5HEX_PROPERTIES = [
            new PropertyDefinition(name: 'value', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CHECK_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CHECK_RESPONSE_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CHECK_REQUEST_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> CHECK_SIZE_PROPERTIES = [
            new PropertyDefinition(name: 'applyTo', required: false),
    ].toSet().asImmutable()
}
