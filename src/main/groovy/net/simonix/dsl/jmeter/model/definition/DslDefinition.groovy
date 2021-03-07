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
    static final KeywordDefinition PLAN = keyword('plan', KeywordCategory.PLAN) {
        include(COMMON_PROPERTIES)
        property(name: 'serialized', type: Boolean, required: false, defaultValue: false)
        property(name: 'functionalMode', type: Boolean, required: false, defaultValue: false)
        property(name: 'tearDownOnShutdown', type: Boolean, required: false, defaultValue: false)
        property(name: 'classpath', type: String, required: false, separator: ',', defaultValue: [])
    }

    // group
    static final KeywordDefinition GROUP = keyword('group', KeywordCategory.GROUP) {
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

    static final KeywordDefinition BEFORE_GROUP = keyword('before', KeywordCategory.GROUP) {
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

    static final KeywordDefinition AFTER_GROUP = keyword('after', KeywordCategory.GROUP) {
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
    static final KeywordDefinition LOOP = keyword('loop', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'count', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'forever', type: Boolean, required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SIMPLE = keyword('simple', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
    }
    
    static final KeywordDefinition TRANSACTION = keyword('transaction', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'timers', type: Boolean, required: false, defaultValue: false)
        property(name: 'generate', type: Boolean, required: false, defaultValue: false)
    }
    
    static final KeywordDefinition SECTION = keyword('section', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'lock', type: String, required: false, defaultValue: 'global_lock')
    }
    
    static final KeywordDefinition INCLUDE = keyword('include', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'file', type: String, required: true)
        leaf()
        valueIsProperty()
    }
    
    static final KeywordDefinition FOR_EACH = keyword('for_each', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'in', type: String, required: false, defaultValue: '')
        property(name: 'out', type: String, required: false, defaultValue: '')
        property(name: 'separator', type: Boolean, required: false, defaultValue: true)
        property(name: 'start', type: Integer, required: false, defaultValue: 0)
        property(name: 'end', type: Integer, required: false, defaultValue: 1)
    }
    
    static final KeywordDefinition EXECUTE = keyword('execute', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['if', 'while', 'once', 'interleave', 'random', 'order', 'percent', 'total', 'runtime', 'switch']))
    }

    static final KeywordDefinition EXECUTE_IF = keyword('execute_if', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'condition', type: String, required: true)
        property(name: 'useExpression', type: Boolean, required: false, defaultValue: true)
        property(name: 'evaluateAll', type: Boolean, required: false, defaultValue: false)
        valueIsProperty()
    }

    static final KeywordDefinition EXECUTE_WHILE = keyword('execute_while', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'condition', type: String, required: true)
        valueIsProperty()
    }

    static final KeywordDefinition EXECUTE_ONCE = keyword('execute_once', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_INTERLEAVE = keyword('execute_interleave', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', type: Boolean, required: false, defaultValue: false)
        property(name: 'acrossUsers', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_RANDOM = keyword('execute_random', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'ignore', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition EXECUTE_ORDER = keyword('execute_order', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
    }

    static final KeywordDefinition EXECUTE_PERCENT = keyword('execute_percent', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'percent', type: Integer, required: false, defaultValue: 100, constraints: range(0, 100))
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
        valueIsProperty()
    }

    static final KeywordDefinition EXECUTE_TOTAL = keyword('execute_total', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'total', type: Integer, required: false, defaultValue: 1, constraints: range(0))
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
        valueIsProperty()
    }

    static final KeywordDefinition EXECUTE_RUNTIME = keyword('execute_runtime', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'runtime', type: Integer, required: false, defaultValue: 1, constraints: range(0))
        valueIsProperty()
    }

    static final KeywordDefinition EXECUTE_SWITCH = keyword('execute_switch', KeywordCategory.CONTROLLER) {
        include(COMMON_PROPERTIES)
        property(name: 'value', type: String, required: false, defaultValue: '0')
        valueIsProperty()
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

    static final KeywordDefinition HTTP = keyword('http', KeywordCategory.SAMPLER) {
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

    static final KeywordDefinition AJP = keyword('ajp', KeywordCategory.SAMPLER) {
        include(COMMON_PROPERTIES)
        include(HTTP_COMMON_PROPERTIES)
        property(name: 'downloadEmbeddedResources', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrent', type: Boolean, required: false, defaultValue: false)
        property(name: 'embeddedConcurrentDownloads', type: Integer, required: false, defaultValue: 6)
        property(name: 'embeddedResourceUrl', type: String, required: false, defaultValue: '')
        property(name: 'saveAsMD5', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition DEBUG = keyword('debug', KeywordCategory.SAMPLER) {
        include(COMMON_PROPERTIES)
        property(name: 'displayJMeterProperties', type: Boolean, required: false, defaultValue: false)
        property(name: 'displayJMeterVariables', type: Boolean, required: false, defaultValue: false)
        property(name: 'displaySystemProperties', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition JSR223_SAMPLER = keyword('jsrsampler', KeywordCategory.SAMPLER) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
    }

    static final KeywordDefinition FLOW = keyword('flow', KeywordCategory.SAMPLER) {
        include(COMMON_PROPERTIES)
        property(name: 'action', type: String, required: false, defaultValue: 'pause', constraints: inList(['pause', 'stop', 'stop_now', 'restart_next_loop', 'start_next', 'break']))
        property(name: 'target', type: String, required: false, defaultValue: 'current', constraints: inList(['current', 'all']))
        property(name: 'duration', type: Integer, required: false, defaultValue: '0', constraints: range(0))
    }

    // common
    static final KeywordDefinition PARAM = keyword('param', KeywordCategory.OTHER) {
        property(name: 'name', type: String, required: false, defaultValue: null)
        property(name: 'value', type: Object, required: false, defaultValue: null)
        property(name: 'encoded', type: Boolean, required: false, defaultValue: false)
        property(name: 'encoding', type: String, required: false, defaultValue: 'UTF-8')
        leaf()
    }

    static final KeywordDefinition PARAMS = keyword('params', KeywordCategory.OTHER) {
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition BODY = keyword('body', KeywordCategory.OTHER) {
        property(name: 'file', type: String, required: false, defaultVabodlue: null)
        property(name: 'inline', type: String, required: false, defaultValue: null)
        property(name: 'encoding', type: String, required: false, defaultValue: 'UTF-8')
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition ARGUMENT = keyword('argument', KeywordCategory.OTHER) {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        leaf()
    }

    static final KeywordDefinition ARGUMENTS = keyword('arguments', KeywordCategory.OTHER) {
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition INSERT = keyword('insert', KeywordCategory.OTHER) {
        property(name: 'file', type: String, required: false, defaultValue: null)
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition FILE = keyword('file', KeywordCategory.OTHER) {
        property(name: 'file', type: String, required: true, defaultValue: null)
        property(name: 'name', type: Object, required: true, defaultValue: null)
        property(name: 'type', type: Boolean, required: true, defaultValue: false)
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition FILES = keyword('files', KeywordCategory.OTHER)

    // configs
    static final KeywordDefinition AUTHORIZATION = keyword('authorization', KeywordCategory.CONFIG) {
        property(name: 'url', type: String, required: false, defaultValue: '')
        property(name: 'username', type: String, required: false, defaultValue: '')
        property(name: 'password', type: String, required: false, defaultValue: '')
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'realm', type: String, required: false, defaultValue: '')
        property(name: 'mechanism', type: String, required: false, defaultValue: 'BASIC', constraints: inList(['BASIC', 'DIGEST', 'KERBEROS', 'BASIC_DIGEST']))
        leaf()
    }

    static final KeywordDefinition AUTHORIZATIONS = keyword('authorizations', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, required: false, defaultValue: false)
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition DNS = keyword('dns', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, required: false, defaultValue: false)
        property(name: 'useSystem', type: Boolean, required: false, defaultValue: true)
        property(name: 'servers', type: String, required: false, defaultValue: [])
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition DNS_HOST = keyword('host', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'address', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition CACHE = keyword('cache', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, required: false, defaultValue: false)
        property(name: 'useExpires', type: Boolean, required: false, defaultValue: true)
        property(name: 'maxSize', type: Integer, required: false, defaultValue: 5000, constraints: range(0))
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
        leaf()
    }

    static final KeywordDefinition COOKIE = keyword('cookie', KeywordCategory.CONFIG) {
        property(name: 'secure', type: Boolean, required: false, defaultValue: false)
        property(name: 'path', type: String,required: false, defaultValue: null)
        property(name: 'domain', type: String, required: false, defaultValue: '')
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'expires', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition COOKIES = keyword('cookies', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'clearEachIteration', type: Boolean, required: false, defaultValue: false)
        property(name: 'policy', type: String, required: false, defaultValue: 'standard', constraints: inList(['standard', 'compatibility', 'netscape', 'standard-strict', 'best-match', 'rfc2109', 'rfc2965', 'default', 'ignoreCookies']))
        property(name: 'useUserConfig', type: Boolean, required: false, defaultValue: false)
    }

    static final KeywordDefinition COUNTER = keyword('counter', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', type: Boolean, required: false, defaultValue: false)
        property(name: 'reset', type: Boolean, required: false, defaultValue: false)
        property(name: 'start', type: Integer, required: false, defaultValue: 0)
        property(name: 'end', type: Integer, required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'increment', type: Integer, required: false, defaultValue: 1)
        property(name: 'variable', type: String, required: false, defaultValue: 'c')
        property(name: 'format', type: String, required: false, defaultValue: '')
        leaf()
    }

    static final KeywordDefinition CSV_DATA = keyword('csv', KeywordCategory.CONFIG) {
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
        leaf()
    }

    static final KeywordDefinition DEFAULTS = keyword('defaults', KeywordCategory.CONFIG) {
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

    static final KeywordDefinition HEADER = keyword('header', KeywordCategory.CONFIG) {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        leaf()
    }

    static final KeywordDefinition HEADERS = keyword('headers', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    static final KeywordDefinition LOGIN = keyword('login', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'username', type: String, required: false, defaultValue: '')
        property(name: 'password', type: String, required: false, defaultValue: '')
    }

    static final KeywordDefinition RANDOM_VARIABLE = keyword('random', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'perUser', type: Boolean, required: false, defaultValue: true)
        property(name: 'minimum', type: Integer, required: false, defaultValue: 0)
        property(name: 'maximum', type: Integer, required: false, defaultValue: Integer.MAX_VALUE)
        property(name: 'format', type: String, required: false, defaultValue: null)
        property(name: 'variable', type: String, required: false, defaultValue: 'r')
        property(name: 'seed', type: Long, required: false, defaultValue: null)
        leaf()
    }

    static final KeywordDefinition VARIABLE = keyword('variable', KeywordCategory.CONFIG) {
        property(name: 'name', type: String, required: false, defaultValue: '')
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'description', type: String, required: false, defaultValue: '')
        leaf()
    }

    static final KeywordDefinition VARIABLES = keyword('variables', KeywordCategory.CONFIG) {
        include(COMMON_PROPERTIES)
        property(name: 'values', type: Map, required: false, defaultValue: [:])
    }

    // timers
    static final KeywordDefinition TIMER = keyword('timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['constant', 'uniform', 'gaussian', 'poisson', 'synchronizing']))
        leaf()
    }

    static final KeywordDefinition CONSTANT_TIMER = keyword('constant_timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 300, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition UNIFORM_TIMER = keyword('uniform_timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 1000, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition GAUSSIAN_TIMER = keyword('gaussian_timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 100, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 300.0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition POISSON_TIMER = keyword('poisson_timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'delay', type: Long, required: false, defaultValue: 300, constraints: range(0))
        property(name: 'range', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition SYNCHRONIZING_TIMER = keyword('synchronizing_timer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'users', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'timeout', type: Long, required: false, defaultValue: 0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition THROUGHPUT = keyword('throughput', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'type', type: String, required: true, constraints: inList(['constant', 'precise']))
        leaf()
    }

    static final KeywordDefinition CONSTANT_THROUGHPUT = keyword('constant_throughput', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'target', type: Double, required: false, defaultValue: 0.0, constraints: range(0))
        property(name: 'basedOn', type: String, required: false, defaultValue: 'user', constraints: inList(['user', 'all_users', 'all_users_shared', 'all_users_in_group', 'all_users_in_group_shared']))
        leaf()
    }

    static final KeywordDefinition PRECISE_THROUGHPUT = keyword('precise_throughput', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        property(name: 'target', type: Double, required: false, defaultValue: 100.0, constraints: range(0))
        property(name: 'period', type: Integer, required: false, defaultValue: 3600, constraints: range(0))
        property(name: 'duration', type: Long, required: false, defaultValue: 3600, constraints: range(0))
        property(name: 'batchUsers', type: Integer, required: false, defaultValue: 1, constraints: range(0))
        property(name: 'batchDelay', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'samples', type: Integer, required: false, defaultValue: 10_000, constraints: range(0))
        property(name: 'percents', type: Double, required: false, defaultValue: 1.0, constraints: range(0))
        property(name: 'seed', type: Long, required: false, defaultValue: 0, constraints: range(0))
        leaf()
    }

    static final KeywordDefinition JSR223_TIMER = keyword('jsrtimer', KeywordCategory.TIMER) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
        leaf()
    }

    // listeners
    static final KeywordDefinition AGGREGATE = keyword('aggregate', KeywordCategory.LISTENER) {
        include(COMMON_PROPERTIES)
        property(name: 'file', type: String, required: true, defaultValue: '')
        leaf()
    }

    static final KeywordDefinition BACKEND = keyword('backend', KeywordCategory.LISTENER)  {
        include(COMMON_PROPERTIES)
        property(name: 'classname', type: String, required: false, defaultValue: 'org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient')
        property(name: 'queueSize', type: Integer, required: false, defaultValue: 5000, constraints: range(1))
    }

    static final KeywordDefinition SUMMARY = keyword('summary', KeywordCategory.LISTENER) {
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
        leaf()
    }

    static final KeywordDefinition JSR223_LISTENER = keyword('jsrlistener', KeywordCategory.LISTENER) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
        leaf()
    }

    // extractors
    static final KeywordDefinition CSS_EXTRACTOR = keyword('extract_css', KeywordCategory.EXTRACTOR) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'useEmptyValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'attribute', type: String, required: false, defaultValue: '')
        property(name: 'engine', type: String, required: false, defaultValue: 'JSOUP', constraints: inList(['JSOUP', 'JODD']))
        leaf()
    }

    static final KeywordDefinition REGEX_EXTRACTOR = keyword('extract_regex', KeywordCategory.EXTRACTOR) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'field', type: String, required: false, defaultValue: 'response_body', constraints: inList(['response_body', 'response_unescaped', 'response_document', 'response_headers', 'response_code', 'response_message', 'request_headers', 'url']))
        property(name: 'useEmptyValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 1, constraints: range(1))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'template', type: String, required: false, defaultValue: '\$1\$')
        leaf()
    }

    static final KeywordDefinition JSON_EXTRACTOR = keyword('extract_json', KeywordCategory.EXTRACTOR) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValues', type: String, required: false, defaultValue: [], separator: ';')
        property(name: 'matches', type: Integer, required: false, defaultValue: [1], separator: ';', constraints: range(1))
        property(name: 'variables', type: String, required: true, defaultValue: [], separator: ';')
        property(name: 'expressions', type: String, required: true, defaultValue: [], separator: ';')
        property(name: 'concatenation', type: Boolean, required: false, defaultValue: false)
        leaf()
    }

    static final KeywordDefinition XPATH_EXTRACTOR = keyword('extract_xpath', KeywordCategory.EXTRACTOR) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'parent', constraints: inList(['parent', 'all', 'children', 'variable']))
        property(name: 'defaultValue', type: String, required: false, defaultValue: '')
        property(name: 'match', type: Integer, required: false, defaultValue: 0, constraints: range(0))
        property(name: 'variable', type: String, required: true, defaultValue: '')
        property(name: 'expression', type: String, required: true, defaultValue: '')
        property(name: 'namespaces', type: String, required: false, defaultValue: [], separator: '\n')
        property(name: 'fragment', type: Boolean, required: false, defaultValue: false)
        leaf()
    }

    static final KeywordDefinition JSR223_POSTPROCESSOR = keyword('jsrpostprocessor', KeywordCategory.POSTPROCESSOR) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
        leaf()
    }

    static final KeywordDefinition JSR223_PREPROCESSOR = keyword('jsrpreprocessor', KeywordCategory.PREPROCESSOR) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
        leaf()
    }

    // assertions
    static final KeywordDefinition JSR223_ASSERTION = keyword('jsrassertion', KeywordCategory.ASSERTION) {
        include(COMMON_PROPERTIES)
        include(JSR223_PROPERTIES)
        leaf()
    }

    static final KeywordDefinition ASSERT_RESPONSE = keyword('assert_response', KeywordCategory.ASSERTION) {
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

    static final KeywordDefinition ASSERT_SIZE = keyword('assert_size', KeywordCategory.ASSERTION) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'variable', type: String, required: false, defaultValue: null)
        property(name: 'field', type: String, required: false, defaultValue: 'response_data', constraints: inList(['response_data', 'response_body', 'response_code', 'response_message', 'response_headers']))
        property(name: 'rule', type: String, required: false, defaultValue: 'eq', constraints: inList(['contains', 'matches', 'equals', 'substring']))
        property(name: 'size', type: Long, required: true, defaultValue: 0, constraints: range(0))
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition ASSERT_DURATION = keyword('assert_duration', KeywordCategory.ASSERTION) {
        include(COMMON_PROPERTIES)
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        property(name: 'duration', type: Long, required: true, defaultValue: 0, constraints: range(0))
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition ASSERT_XPATH = keyword('assert_xpath', KeywordCategory.ASSERTION) {
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
        leaf()
    }

    static final KeywordDefinition ASSERT_JSON = keyword('assert_json', KeywordCategory.ASSERTION) {
        include(COMMON_PROPERTIES)
        property(name: 'jpath', type: String, required: false, defaultValue: '$.')
        property(name: 'assertValue', type: Boolean, required: false, defaultValue: false)
        property(name: 'assertAsRegex', type: Boolean, required: false, defaultValue: true)
        property(name: 'value', type: String, required: false, defaultValue: '')
        property(name: 'expectNull', type: Boolean, required: false, defaultValue: false)
        property(name: 'invert', type: Boolean, required: false, defaultValue: false)
        leaf()
    }

    static final KeywordDefinition ASSERT_MD5HEX = keyword('assert_md5hex', KeywordCategory.ASSERTION) {
        include(COMMON_PROPERTIES)
        property(name: 'value', type: String, required: true, defaultValue: '')
        leaf()
        valueIsProperty()
    }

    static final KeywordDefinition CHECK_RESPONSE = keyword('check_response', KeywordCategory.CHECK) {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        valueIsProperty()
    }

    static final KeywordDefinition CHECK_REQUEST = keyword('check_request', KeywordCategory.CHECK) {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        valueIsProperty()
    }

    static final KeywordDefinition CHECK_SIZE = keyword('check_size', KeywordCategory.CHECK) {
        property(name: 'applyTo', type: String, required: false, defaultValue: 'all', constraints: inList(['all', 'parent', 'children', 'variable']))
        valueIsProperty()
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
            DNS.name,
            DNS_HOST.name,
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
