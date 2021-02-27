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
package net.simonix.dsl.jmeter.model.definition

import groovy.transform.CompileDynamic

import static net.simonix.dsl.jmeter.model.constraint.Constraints.inList
import static net.simonix.dsl.jmeter.model.constraint.Constraints.range
import static net.simonix.dsl.jmeter.model.definition.DefinitionBuilder.keyword
import static net.simonix.dsl.jmeter.model.definition.DefinitionBuilder.properties

@CompileDynamic
final class DslDefinition {
    static final Set<PropertyDefinition> COMMON_PROPERTIES = properties {
        property(name: 'name', type: String, required: false)
        property(name: 'comments', type: String, required: false, defaultValue: '')
        property(name: 'enabled', type: Boolean, required: false, defaultValue: true)
    }

    // plan
    static final KeywordDefinition PLAN = keyword('plan') {
        include(COMMON_PROPERTIES)
        property(name: 'serialized', type: Boolean, required: false, defaultValue: false)
        property(name: 'functionalMode', type: Boolean, required: false, defaultValue: false)
        property(name: 'tearDownOnShutdown', type: Boolean, required: false, defaultValue: false)
        property(name: 'classpath', type: String, required: false, separator: ',', defaultValue: [])
    }

    // group
    static final KeywordDefinition GROUP = keyword('group') {
        include(COMMON_PROPERTIES)
        property(name: 'users', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'rampUp', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'delayedStart', type: Boolean, required: false, defaultValue: false)
        property(name: 'keepUser', type: Boolean, required: false, defaultValue: true)
        property(name: 'scheduler', type: Boolean, required: false, defaultValue: false)
        property(name: 'delay', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'duration', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'loops', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', type: Boolean, required: false, defaultValue: false)
        property(name: 'onError', type: String, required: false, defaultValue: 'continue', constraints: inList(['continue', 'start_next', 'stop_user', 'stop_test', 'stop_now']))
    }

    static final KeywordDefinition BEFORE_GROUP = keyword('before') {
        include(COMMON_PROPERTIES)
        property(name: 'users', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'rampUp', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'keepUser', type: Boolean, required: false, defaultValue: true)
        property(name: 'scheduler', type: Boolean, required: false, defaultValue: false)
        property(name: 'delay', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'duration', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'loops', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', type: Boolean, required: false, defaultValue: false)
        property(name: 'onError', type: String, required: false, defaultValue: 'continue', constraints: inList(['continue', 'start_next', 'stop_user', 'stop_test', 'stop_now']))
    }

    static final KeywordDefinition AFTER_GROUP = keyword('after') {
        include(COMMON_PROPERTIES)
        property(name: 'users', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'rampUp', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'keepUser', type: Boolean, required: false, defaultValue: true)
        property(name: 'scheduler', type: Boolean, required: false, defaultValue: false)
        property(name: 'delay', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'duration', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'loops', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', type: Boolean, required: false, defaultValue: false)
        property(name: 'onError', type: String, required: false, defaultValue: 'continue', constraints: inList(['continue', 'start_next', 'stop_user', 'stop_test', 'stop_now']))
    }

    // controllers
    static final KeywordDefinition LOOP = keyword('loop') {
        include(COMMON_PROPERTIES)
        property(name: 'count', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', type: Boolean, required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SIMPLE = keyword('simple') {
        include(COMMON_PROPERTIES)
    }
    
    static final KeywordDefinition TRANSACTION = keyword('transaction') {
        include(COMMON_PROPERTIES)
        property(name: 'timers', type: Boolean, required: false, defaultValue: false)
        property(name: 'generate', type: Boolean, required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SECTION = keyword('section') {
        include(COMMON_PROPERTIES)
        property(name: 'lock', type: String, required: false, defaultValue: 'global_lock')
    }
    
    static final KeywordDefinition INCLUDE = keyword('include') {
        include(COMMON_PROPERTIES)
        property(name: 'file', type: String, required: true)
    }
    
    static final KeywordDefinition FOR_EACH = keyword('for_each') {
        include(COMMON_PROPERTIES)
        property(name: 'in', type: String, required: false, defaultValue: '')
        property(name: 'out', type: String, required: false, defaultValue: '')
        property(name: 'separator', type: Boolean, required: false, defaultValue: true)
        property(name: 'start', type: Integer, required: false, defaultValue: 0)
        property(name: 'end', type: Integer, required: false, defaultValue: 1)
    }
    
    static final KeywordDefinition EXECUTE = keyword('execute') {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['if', 'while', 'once', 'interleave', 'random', 'order', 'percent', 'total', 'runtime', 'switch']))
    }

    static final KeywordDefinition EXECUTE_IF = keyword('execute_if') {
        include(COMMON_PROPERTIES)
        property(name: 'condition', type: String, required: true)
        property(name: 'useExpression', type: Boolean, required: false, defaultValue: true)
        property(name: 'evaluateAll', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_WHILE = keyword('execute_while') {
        include(COMMON_PROPERTIES)
        property(name: 'condition', type: String, required: true)
    }

    static final KeywordDefinition EXECUTE_ONCE = keyword('execute_once') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_INTERLEAVE = keyword('execute_interleave') {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', type: Boolean, required: false, defaultValue: false)
        property(name: 'acrossUsers', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_RANDOM = keyword('execute_random') {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_ORDER = keyword('execute_order') {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_PERCENT = keyword('execute_percent') {
        include(COMMON_PROPERTIES)
        property(name: 'percent', type: Integer, required: false, defaultValue: 100, constraints: range(0, 100))
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_TOTAL = keyword('execute_total') {
        include(COMMON_PROPERTIES)
        property(name: 'total', type: Integer, required: false, defaultValue: 1, constraints: range(0))
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_RUNTIME = keyword('execute_runtime') {
        include(COMMON_PROPERTIES)
        property(name: 'runtime', type: Integer, required: false, defaultValue: 1, constraints: range(0))
    }

    static final KeywordDefinition EXECUTE_SWITCH = keyword('execute_switch') {
        include(COMMON_PROPERTIES)
        property(name: 'value', type: String, required: false, defaultValue: '0')
    }

    // samplers
    static final Set<PropertyDefinition> JSR223_PROPERTIES = properties {
        property(name: 'inline', type: String, required: false, defaultValue: '')
        property(name: 'cacheKey', type: Boolean, required: false, defaultValue: true)
        property(name: 'file', type: String, required: false, defaultValue: '')
        property(name: 'parameters', type: String, required: false, defaultValue: '', separator: ' ')
        property(name: 'language', type: String, required: false, defaultValue: 'groovy', constraints: inList(['groovy']))
    }

    static final Set<PropertyDefinition> HTTP_COMMON_PROPERTIES = properties {
        property(name: 'method', type: String, required: false, defaultValue: '')
        property(name: 'protocol', type: String, required: false, defaultValue: '')
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'port', type: Integer, required: false, constraints: range(1, 65535))
        property(name: 'path', type: String, required: false, defaultValue: '')
        property(name: 'encoding', type: String, required: false, defaultValue: '')
        property(name: 'autoRedirects', type: Boolean, required: false, defaultValue: false)
        property(name: 'followRedirects', type: Boolean, required: false, defaultValue: true)
        property(name: 'keepAlive', type: Boolean, required: false, defaultValue: true)
        property(name: 'multipart', type: Boolean, required: false, defaultValue: false)
        property(name: 'browserCompatibleMultipart', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition HTTP = keyword('http') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'impl', type: String, required: false, defaultValue: '')
        property(name: 'connectTimeout', type: String, required: false, defaultValue: '')
        property(name: 'responseTimeout', type: String, required: false, defaultValue: '')
        property(name: 'downloadEmbeddedResources', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', type: Integer, required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', type: String, required: false, defaultValue: '')
        property(name: 'ipSource', type: String, required: false, defaultValue: '')
        property(name: 'ipSourceType', type: String, required: false, defaultValue: null, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', type: String, required: false, defaultValue: '')
        property(name: 'proxyHost', type: String, required: false, defaultValue: '')
        property(name: 'proxyPort', type: String, required: false, defaultValue: '')
        property(name: 'proxyUser', type: String, required: false, defaultValue: '')
        property(name: 'proxyPassword', type: String, required: false, defaultValue: '')
        property(name: 'saveAsMD5', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition AJP = keyword('ajp') {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'downloadEmbeddedResources', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', type: Integer, required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', type: String, required: false, defaultValue: '')
        property(name: 'saveAsMD5', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition DEBUG = keyword('debug') {
        include(COMMON_PROPERTIES)
        property(name: 'displayJMeterProperties', type: Boolean, required: false, defaultValue: false)
        property(name: 'displayJMeterVariables', type: Boolean, required: false, defaultValue: false)
        property(name: 'displaySystemProperties', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition JSR223_SAMPLER = keyword('jsrsampler') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition FLOW = keyword('flow') {
        include(COMMON_PROPERTIES)
        property(name: 'action', type: String, required: false, defaultValue: 'pause', constraints: inList(['pause', 'stop', 'stop_now', 'restart_next_loop', 'start_next', 'break']))
        property(name: 'target', type: String, required: false, defaultValue: 'current', constraints: inList(['current', 'all']))
        property(name: 'duration', type: Integer, required: false, defaultValue: '0', constraints: range(0))
    }

    // common
    static final KeywordDefinition PARAM = keyword('param') {
        property(name: 'name', type: String, required: false, defaultValue: null)
        property(name: 'value', type: Object, required: false, defaultValue: null)
        property(name: 'encoded', type: Boolean, required: false, defaultValue: false)
        property(name: 'encoding', type: String, required: false, defaultValue: 'UTF-8')
    }

    static final KeywordDefinition PARAMS = keyword('params') {
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition BODY = keyword('body') {
        property(name: 'file', type: String, required: false, defaultVabodlue: null)
        property(name: 'inline', type: String, required: false, defaultValue: null)
        property(name: 'encoding', type: String, required: false, defaultValue: 'UTF-8')
    }

    static final KeywordDefinition ARGUMENT = keyword('argument') {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition ARGUMENTS = keyword('arguments') {
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition INSERT = keyword('insert') {
        property(name: 'file', type: String, required: false, defaultValue: null)
    }

    static final KeywordDefinition FILE = keyword('file') {
        property(name: 'file', type: String, required: true, defaultValue: null)
        property(name: 'name', type: Object, required: true, defaultValue: null)
        property(name: 'type', type: Boolean, required: true, defaultValue: false)
    }

    static final KeywordDefinition FILES = keyword('files')

    // configs
    static final KeywordDefinition AUTHORIZATION = keyword('authorization') {
        property(name: 'url', type: String, required: false, defaultValue: '')
        property(name: 'username', type: String, required: false, defaultValue: '')
        property(name: 'password', type: String, required: false, defaultValue: '')
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'realm', type: String, required: false, defaultValue: '')
        property(name: 'mechanism', type: String, required: false, defaultValue: 'BASIC', constraints: inList(['BASIC', 'DIGEST', 'KERBEROS', 'BASIC_DIGEST']))
    }

    static final KeywordDefinition AUTHORIZATIONS = keyword('authorizations') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, perequired: false, defaultValue: false)
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition CACHE = keyword('cache') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, perequired: false, defaultValue: false)
        property(name: 'useExpires', type: Boolean, required: false, defaultValue: true)
        property(name: 'maxSize', type: Integer, required: false, defaultValue: 5000, constraints: range(0))
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition COOKIE = keyword('cookie') {
        property(name: 'secure', type: Boolean, required: false, defaultValue: false)
        property(name: 'path', type: String,required: false, defaultValue: null)
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'expires', type: Integer, required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition COOKIES = keyword('cookies') {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, required: false, defaultValue: false)
        property(name: 'policy', type: String, required: false, defaultValue: 'standard', constraints: inList(['standard', 'compatibility', 'netscape', 'standard-strict', 'best-match', 'rfc2109', 'rfc2965', 'default', 'ignoreCookies']))
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition COUNTER = keyword('counter') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
        property(name: 'reset', type: Boolean, required: false, defaultValue: false)
        property(name: 'start', type: Integer, required: false, defaultValue: 0)
        property(name: 'end', type: Integer, required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'increment', type: Integer, required: false, defaultValue: 1)
        property(name: 'variable', type: String, required: false, defaultValue: 'c')
        property(name: 'format', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition CSV_DATA = keyword('csv') {
        include(COMMON_PROPERTIES)
        property(name: 'ignoreFirstLine', type: Boolean, required: false, defaultValue: false)
        property(name: 'allowQuotedData', type: Boolean, required: false, defaultValue: false)
        property(name: 'recycle', type: Boolean, required: false, defaultValue: true)
        property(name: 'stopUser', type: Boolean, required: false, defaultValue: false)
        property(name: 'variables', type: String, required: true, defaultValue: [], separator: ',')
        property(name: 'file', type: String, required: true, defaultValue: '')
        property(name: 'encoding', type: String, required: false, defaultValue: 'UTF-8')
        property(name: 'delimiter', type: String, required: false, defaultValue: ',')
        property(name: 'shareMode', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'group', 'thread' ]))
    }

    static final KeywordDefinition DEFAULTS = keyword('defaults') {
        include(COMMON_PROPERTIES)
        property(name: 'protocol', type: String, required: false, defaultValue: 'http')
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'port', type: Integer, required: false, defaultValue: 80)
        property(name: 'method', type: String, required: false, defaultValue: 'GET')
        property(name: 'path', type: String, required: false, defaultValue: '')
        property(name: 'encoding', type: String, required: false, defaultValue: '')
        property(name: 'impl', type: String, required: false, defaultValue: '')
        property(name: 'connectTimeout', type: String, required: false, defaultValue: '')
        property(name: 'responseTimeout', type: String, required: false, defaultValue: '')
        property(name: 'downloadEmbeddedResources', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', type: Integer, required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', type: String, required: false, defaultValue: '')
        property(name: 'ipSource', type: String, required: false, defaultValue: '')
        property(name: 'ipSourceType', type: String, required: false, defaultValue: null, constraints: inList(['hostname', 'device', 'deviceIp4', 'deviceIp6']))
        property(name: 'proxySchema', type: String, required: false, defaultValue: '')
        property(name: 'proxyHost', type: String, required: false, defaultValue: '')
        property(name: 'proxyPort', type: String, required: false, defaultValue: '')
        property(name: 'proxyUser', type: String, required: false, defaultValue: '')
        property(name: 'proxyPassword', type: String, required: false, defaultValue: '')
        property(name: 'saveAsMD5', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition HEADER = keyword('header') {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition HEADERS = keyword('headers') {
        include(COMMON_PROPERTIES)
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition LOGIN = keyword('login') {
        include(COMMON_PROPERTIES)
        property(name: 'username', type: String, required: false, defaultValue: '')
        property(name: 'password', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition RANDOM_VARIABLE = keyword('random') {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', type: Boolean, required: false, defaultValue: true)
        property(name: 'minimum', type: Integer, required: false, defaultValue: 0)
        property(name: 'maximum', type: Integer, required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'format', type: String, required: false, defaultValue: null)
        property(name: 'variable', type: String, required: false, defaultValue: 'r')
        property(name: 'seed', type: Long, required: false, defaultValue: null)
    }

    static final KeywordDefinition VARIABLE = keyword('variable') {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'description', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition VARIABLES = keyword('variables') {
        include(COMMON_PROPERTIES)
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    // timers
    static final KeywordDefinition TIMER = keyword('timer') {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['constant', 'uniform', 'gaussian', 'poisson', 'synchronizing']))
    }

    static final KeywordDefinition CONSTANT_TIMER = keyword('constant_timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 300, constraints: range(0))
    }

    static final KeywordDefinition UNIFORM_TIMER = keyword('uniform_timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 1000, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
    }

    static final KeywordDefinition GAUSSIAN_TIMER = keyword('gaussian_timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 100, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 300.0, constraints: range(0))
    }

    static final KeywordDefinition POISSON_TIMER = keyword('poisson_timer') {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 300, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
    }

    static final KeywordDefinition SYNCHRONIZING_TIMER = keyword('synchronizing_timer') {
        include(COMMON_PROPERTIES)
        property(name: 'users', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'timeout', type: Long, required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition THROUGHPUT = keyword('throughput') {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['constant', 'precise']))
    }

    static final KeywordDefinition CONSTANT_THROUGHPUT = keyword('constant_throughput') {
        include(COMMON_PROPERTIES)
        property(name: 'target', type: Double, required: false, defaultValue: 0.0, constraints: range(0))
        property(name: 'basedOn', type: String, required: false, defaultValue: 'user', constraints: inList(['user', 'all_users', 'all_users_shared', 'all_users_in_group', 'all_users_in_group_shared']))
    }

    static final KeywordDefinition PRECISE_THROUGHPUT = keyword('precise_throughput') {
        include(COMMON_PROPERTIES)
        property(name: 'target', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
        property(name: 'period', type: Integer, required: false, defaultValue: 3600, constraints: range(0))
        property(name: 'duration', type: Long, required: false, defaultValue: 3600, constraints: range(0))
        property(name: 'batchUsers', type: Integer, required: false, defaultValue: 1, constraints: range(0))
        property(name: 'batchDelay', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'samples', type: Integer, required: false, defaultValue: 10_000, constraints: range(0))
        property(name: 'percents', type: Double, required: false, defaultValue: 1.0, constraints: range(0))
        property(name: 'seed', type: Long, required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition JSR223_TIMER = keyword('jsrtimer') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    // listeners
    static final KeywordDefinition AGGREGATE = keyword('aggregate') {
        include(COMMON_PROPERTIES)
        property(name: 'file', type: String, required: true, defaultValue: '')
    }

    static final KeywordDefinition BACKEND = keyword('backend')  {
        include(COMMON_PROPERTIES)
        property(name: 'classname', type: String, required: false, defaultValue: 'org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient')
        property(name: 'queueSize', type: Integer, required: false, defaultValue: 5000, constraints: range(1))
    }

    static final KeywordDefinition SUMMARY = keyword('summary') {
        include(COMMON_PROPERTIES)
        property(name: 'file', type: String, required: true, defaultValue: '')
        property(name: 'errorsOnly', type: Boolean, required: false, defaultValue: false)
        property(name: 'successesOnly', type: Boolean, required: false, defaultValue: false)
        property(name: 'assertions', type: Boolean, required: false)
        property(name: 'bytes', type: Boolean, required: false)
        property(name: 'code', type: Boolean, required: false)
        property(name: 'connectTime', type: Boolean, required: false)
        property(name: 'dataType', type: Boolean, required: false)
        property(name: 'encoding', type: Boolean, required: false)
        property(name: 'fieldNames', type: Boolean, required: false)
        property(name: 'fileName', type: Boolean, required: false)
        property(name: 'hostname', type: Boolean, required: false)
        property(name: 'idleTime', type: Boolean, required: false)
        property(name: 'label', type: Boolean, required: false)
        property(name: 'latency', type: Boolean, required: false)
        property(name: 'message', type: Boolean, required: false)
        property(name: 'requestHeaders', type: Boolean, required: false)
        property(name: 'responseData', type: Boolean, required: false)
        property(name: 'responseHeaders', type: Boolean, required: false)
        property(name: 'sampleCount', type: Boolean, required: false)
        property(name: 'samplerData', type: Boolean, required: false)
        property(name: 'sentBytes', type: Boolean, required: false)
        property(name: 'subresults', type: Boolean, required: false)
        property(name: 'success', type: Boolean, required: false)
        property(name: 'threadCounts', type: Boolean, required: false)
        property(name: 'threadName', type: Boolean, required: false)
        property(name: 'time', type: Boolean, required: false)
        property(name: 'timestamp', type: Boolean, required: false)
        property(name: 'url', type: Boolean, required: false)
        property(name: 'xml', type: Boolean, required: false)
    }

    static final KeywordDefinition JSR223_LISTENER = keyword('jsrlistener') {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    // extractors
    static final KeywordDefinition CSS_EXTRACTOR = keyword('extract_css') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'useEmptyValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'attribute', type: String, required: false, defaultValue: '')
        property(name: 'engine', type: String, required: false, defaultValue: 'JSOUP', constraints: inList(['JSOUP', 'JODD']))
    }

    static final KeywordDefinition REGEX_EXTRACTOR = keyword('extract_regex') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'field', type: String, required: false, defaultValue: 'response_body', constraints: inList(['response_body', 'response_unescaped', 'response_document', 'response_headers', 'response_code', 'response_message', 'request_headers', 'url']))
        property(name: 'useEmptyValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'template', type: String, required: false, defaultValue: '\$1\$')
    }

    static final KeywordDefinition JSON_EXTRACTOR = keyword('extract_json') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValues', type: String, required: false, defaultValue: [], separator: ';')
        property(name: 'matches', type: Integer, required: false, defaultValue: [1], separator: ';', constraints: range(1))
        property(name: 'variables', type: String, required: true, defaultValue: [], separator: ';')
        property(name: 'expressions', type: String, required: true, defaultValue: [], separator: ';')
        property(name: 'concatenation', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition XPATH_EXTRACTOR = keyword('extract_xpath') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'namespaces', type: String, required: false, defaultValue: [], separator: '\n')
        property(name: 'fragment', type: Boolean, required: false, defaultValue: false)
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
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', type: String, required: false, defaultValue: null)
        property(name: 'field', type: String, required: false, defaultValue: 'response_data', constraints: inList(['response_data', 'response_document', 'response_code', 'response_message', 'response_headers', 'request_data', 'request_headers', 'url']))
        property(name: 'message', type: String, required: false, defaultValue: '')
        property(name: 'rule', type: String, required: false, defaultValue: 'contains', constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'ignoreStatus', type: Boolean, required: false, defaultValue: false)
        property(name: 'any', type: Boolean, required: false, defaultValue: false)
        property(name: 'negate', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition ASSERT_SIZE = keyword('assert_size') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', type: String, required: false, defaultValue: null)
        property(name: 'field', type: String, required: false, defaultValue: 'response_data', constraints: inList(['response_data', 'response_body', 'response_code', 'response_message', 'response_headers']))
        property(name: 'rule', type: String, required: false, defaultValue: 'eq', constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'size', type: Long, required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_DURATION = keyword('assert_duration') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'duration', type: Long, required: false, defaultValue: 0, constraints: range(0))
    }

    static final KeywordDefinition ASSERT_XPATH = keyword('assert_xpath') {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', type: String, required: false, defaultValue: null)
        property(name: 'xpath', type: String, required: false, defaultValue: '/')
        property(name: 'ignoreWhitespace', type: Boolean, required: false, defaultValue: false)
        property(name: 'validate', type: Boolean, required: false, defaultValue: false)
        property(name: 'useNamespace', type: Boolean, required: false, defaultValue: false)
        property(name: 'fetchDtd', type: Boolean, required: false, defaultValue: false)
        property(name: 'failOnNoMatch', type: Boolean, required: false, defaultValue: false)
        property(name: 'useTolerant', type: Boolean, required: false, defaultValue: false)
        property(name: 'reportErrors', type: Boolean, required: false, defaultValue: false)
        property(name: 'showWarnings', type: Boolean, required: false, defaultValue: false)
        property(name: 'quiet', type: Boolean, required: false, defaultValue: true)
    }

    static final KeywordDefinition ASSERT_JSON = keyword('assert_json') {
        include(COMMON_PROPERTIES)
        property(name: 'jpath', type: String, required: false, defaultValue: '$.')
        property(name: 'assertValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'assertAsRegex', type: Boolean, required: false, defaultValue: true)
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'expectNull', type: Boolean, required: false, defaultValue: false)
        property(name: 'invert', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition ASSERT_MD5HEX = keyword('assert_md5hex') {
        include(COMMON_PROPERTIES)
        property(name: 'value', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition CHECK_RESPONSE = keyword('check_response') {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_REQUEST = keyword('check_request') {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final KeywordDefinition CHECK_SIZE = keyword('check_size') {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
    }

    static final Set<String> VALID_KEYWORDS = [
            PLAN.name,
            GROUP.name,
            BEFORE_GROUP.name,
            AFTER_GROUP.name,

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
            FILE.name,
            FILES.name,
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

            TIMER.name,
            CONSTANT_TIMER.name,
            UNIFORM_TIMER.name,
            GAUSSIAN_TIMER.name,
            POISSON_TIMER.name,
            THROUGHPUT.name,
            CONSTANT_THROUGHPUT.name,
            PRECISE_THROUGHPUT.name,
            SYNCHRONIZING_TIMER.name,
            JSR223_TIMER.name,

            AGGREGATE.name,
            BACKEND.name,
            SUMMARY.name,
            JSR223_LISTENER.name,

            CSS_EXTRACTOR.name,
            REGEX_EXTRACTOR.name,
            JSON_EXTRACTOR.name,
            XPATH_EXTRACTOR.name,

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
