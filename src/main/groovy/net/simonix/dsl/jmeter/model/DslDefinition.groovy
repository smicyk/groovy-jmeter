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
    static final String PLAN = 'plan'
    static final String GROUP = 'group'

    static final String LOOP = 'loop'
    static final String SIMPLE = 'simple'
    static final String TRANSACTION = 'transaction'
    static final String SECTION = 'section'
    static final String INCLUDE = 'include'
    static final String FOR_EACH = 'for_each'

    static final String EXECUTE = 'execute'

    static final String EXECUTE_IF = 'execute_if'
    static final String EXECUTE_WHILE = 'execute_while'
    static final String EXECUTE_ONCE = 'execute_once'
    static final String EXECUTE_INTERLEAVE = 'execute_interleave'
    static final String EXECUTE_RANDOM = 'execute_random'
    static final String EXECUTE_ORDER = 'execute_order'
    static final String EXECUTE_PERCENT = 'execute_percent'
    static final String EXECUTE_TOTAL = 'execute_total'
    static final String EXECUTE_RUNTIME = 'execute_runtime'
    static final String EXECUTE_SWITCH = 'execute_switch'

    static final String EXEC_IF = 'exec_if'
    static final String EXEC_WHILE = 'exec_while'
    static final String EXEC_ONCE = 'exec_once'
    static final String EXEC_INTERLEAVE = 'exec_interleave'
    static final String EXEC_RANDOM = 'exec_random'
    static final String EXEC_ORDER = 'exec_order'
    static final String EXEC_PERCENT = 'exec_percent'
    static final String EXEC_TOTAL = 'exec_total'
    static final String EXEC_RUNTIME = 'exec_runtime'
    static final String EXEC_SWITCH = 'exec_switch'

    static final String HTTP = 'http'
    static final String AJP = 'ajp'
    static final String DEBUG = 'debug'
    static final String JSR223_SAMPLER = 'jsrsampler'
    static final String FLOW = 'flow'

    static final String PARAM = 'param'
    static final String PARAMS = 'params'
    static final String BODY = 'body'
    static final String ARGUMENT = 'argument'
    static final String ARGUMENTS = 'arguments'
    static final String INSERT = 'insert'

    static final String AUTHORIZATION = 'authorization'
    static final String AUTHORIZATIONS = 'authorizations'
    static final String CACHE = 'cache'
    static final String COOKIE = 'cookie'
    static final String COOKIES = 'cookies'
    static final String COUNTER = 'counter'
    static final String CSV_DATA = 'csv'
    static final String DEFAULTS = 'defaults'
    static final String HEADER = 'header'
    static final String HEADERS = 'headers'
    static final String LOGIN = 'login'
    static final String RANDOM_VARIABLE = 'random'
    static final String VARIABLE = 'variable'
    static final String VARIABLES = 'variables'

    static final String CONSTANT_TIMER = 'timer'
    static final String UNIFORM_TIMER = 'uniform'
    static final String JSR223_TIMER = 'jsrtimer'

    static final String AGGREGATE = 'aggregate'
    static final String BACKEND = 'backend'
    static final String SUMMARY = 'summary'
    static final String JSR223_LISTENER = 'jsrlistener'

    static final String CSS_EXTRACTOR = 'extract_css'
    static final String REGEX_EXTRACTOR = 'extract_regex'
    static final String JSON_EXTRACTOR = 'extract_json'

    static final String JSR223_POSTPROCESSOR = 'jsrpostprocessor'
    static final String JSR223_PREPROCESSOR = 'jsrpreprocessor'

    static final String JSR223_ASSERTION = 'jsrassertion'
    static final String ASSERT_RESPONSE = 'assert_response'
    static final String ASSERT_SIZE = 'assert_size'
    static final String ASSERT_DURATION = 'assert_duration'
    static final String ASSERT_XPATH = 'assert_xpath'
    static final String ASSERT_JSON = 'assert_json'
    static final String ASSERT_MD5HEX = 'assert_md5hex'
    static final String CHECK_RESPONSE = 'check_response'
    static final String CHECK_REQUEST = 'check_request'
    static final String CHECK_SIZE = 'check_size'

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
            new PropertyDefinition(name: 'path', required: true),
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
            new PropertyDefinition(name: 'path', required: false),
            new PropertyDefinition(name: 'encoding', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> INSERT_PROPERTIES = [
            new PropertyDefinition(name: 'path', required: false),
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
            new PropertyDefinition(name: 'path', required: false),
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
            new PropertyDefinition(name: 'variables', required: false),
            new PropertyDefinition(name: 'filename', required: false),
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
            new PropertyDefinition(name: 'script', required: false),
            new PropertyDefinition(name: 'cacheKey', required: false),
            new PropertyDefinition(name: 'filename', required: false),
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
            new PropertyDefinition(name: 'path', required: true),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> BACKEND_PROPERTIES = [
            new PropertyDefinition(name: 'classname', required: false),
            new PropertyDefinition(name: 'queueSize', required: false),
    ].toSet().asImmutable()

    static final Set<PropertyDefinition> SUMMARY_PROPERTIES = [
            new PropertyDefinition(name: 'path', required: true),
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
