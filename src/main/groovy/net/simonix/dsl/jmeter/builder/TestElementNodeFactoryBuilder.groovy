/*
 * Copyright 2019 Szymon Micyk
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
package net.simonix.dsl.jmeter.builder

import net.simonix.dsl.jmeter.factory.assertion.*
import net.simonix.dsl.jmeter.factory.common.*
import net.simonix.dsl.jmeter.factory.config.*
import net.simonix.dsl.jmeter.factory.controller.*
import net.simonix.dsl.jmeter.factory.controller.execution.*
import net.simonix.dsl.jmeter.factory.extractor.CssSelectorExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.JsonPathExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.RegExExtractorFactory
import net.simonix.dsl.jmeter.factory.group.GroupFactory
import net.simonix.dsl.jmeter.factory.listener.AggregateFactory
import net.simonix.dsl.jmeter.factory.listener.BackendListenerFactory
import net.simonix.dsl.jmeter.factory.listener.JSR223ListenerFactory
import net.simonix.dsl.jmeter.factory.listener.SummaryFactory
import net.simonix.dsl.jmeter.factory.plan.PlanFactory
import net.simonix.dsl.jmeter.factory.postprocessor.JSR223PostProcessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.JSR223PreProcessorFactory
import net.simonix.dsl.jmeter.factory.sampler.*
import net.simonix.dsl.jmeter.factory.timer.ConstantTimerFactory
import net.simonix.dsl.jmeter.factory.timer.JSR223TimerFactory
import net.simonix.dsl.jmeter.factory.timer.UniformRandomTimeFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Handles all DSL keywords and builds final {@link net.simonix.dsl.jmeter.model.TestElementNode} tree.
 */
class TestElementNodeFactoryBuilder extends FactoryBuilderSupport {

    private static final Logger logger = LoggerFactory.getLogger(TestElementNodeFactoryBuilder)

    TestElementNodeFactoryBuilder(boolean init = true) {
        super(init)
    }

    void registerObjectFactories() {
        // plan and group
        registerFactory('plan', new PlanFactory('Test Plan'))
        registerFactory('group', new GroupFactory('User Group'))

        // controllers
        registerFactory('loop', new LoopFactory('Loop Controller'))
        registerFactory('simple', new SimpleFactory('Simple Controller'))
        registerFactory('transaction', new TransactionFactory('Transaction Controller'))
        registerFactory('section', new CriticalSectionFactory('Critical Section Controller'))
        registerFactory('include', new IncludeFactory('Include Controller'))
        registerFactory('for_each', new ForEachFactory('ForEach Controller'))

        registerFactory('execute', new ExecuteFactory())

        registerFactory('execute_if', new IfControllerFactory())
        registerFactory('execute_while', new WhileControllerFactory())
        registerFactory('execute_once', new OnceControllerFactory())
        registerFactory('execute_interleave', new InterleaveControllerFactory())
        registerFactory('execute_random', new RandomControllerFactory())
        registerFactory('execute_order', new RandomOrderControllerFactory())
        registerFactory('execute_percent', new PercentControllerFactory())
        registerFactory('execute_total', new TotalControllerFactory())
        registerFactory('execute_runtime', new RuntimeControllerFactory())
        registerFactory('execute_switch', new SwitchControllerFactory())

        registerFactory('exec_if', new IfControllerFactory())
        registerFactory('exec_while', new WhileControllerFactory())
        registerFactory('exec_once', new OnceControllerFactory())
        registerFactory('exec_interleave', new InterleaveControllerFactory())
        registerFactory('exec_random', new RandomControllerFactory())
        registerFactory('exec_order', new RandomOrderControllerFactory())
        registerFactory('exec_percent', new PercentControllerFactory())
        registerFactory('exec_total', new TotalControllerFactory())
        registerFactory('exec_runtime', new RuntimeControllerFactory())
        registerFactory('exec_switch', new SwitchControllerFactory())

        // samplers
        registerFactory('http', new HttpFactory('HTTP Sampler'))
        registerFactory('ajp', new AjpFactory('AJP Sampler'))
        registerFactory('debug', new DebugFactory('Debug Sampler'))
        registerFactory('jsrsampler', new JSR223SamplerFactory('JSR223 Sampler'))
        registerFactory('flow', new FlowControlActionFactory('Flow Control Action'))

        // others
        registerFactory('param', new ParamFactory())
        registerFactory('params', new ParamsFactory())
        registerFactory('body', new BodyFactory())
        registerFactory('argument', new ArgumentFactory())
        registerFactory('arguments', new ArgumentsFactory())

        // timers
        registerFactory('timer', new ConstantTimerFactory('Constant Timer'))
        registerFactory('uniform', new UniformRandomTimeFactory('Uniform Random Timer'))
        registerFactory('jsrtimer', new JSR223TimerFactory('JSR223 Timer'))

        // extractors
        registerFactory('extract_regex', new RegExExtractorFactory('Regular Expression Extractor'))
        registerFactory('extract_css', new CssSelectorExtractorFactory('CSS Selector Extractor'))
        registerFactory('extract_json', new JsonPathExtractorFactory('Json Path Extractor'))

        // assertions
        registerFactory('jsrassertion', new JSR223AssertionFactory('JSR223 Assertion'))
        registerFactory('assert_response', new ResponseAssertionFactory('Response Assertion'))
        registerFactory('assert_size', new SizeAssertionFactory('Size Assertion'))
        registerFactory('assert_duration', new DurationAssertionFactory('Duration Assertion'))
        registerFactory('assert_xpath', new XPathAssertionFactory('XPath Assertion'))
        registerFactory('assert_json', new JsonAssertionFactory('Json Path Assertion'))
        registerFactory('assert_md5hex', new MD5HexAssertionFactory('MD5Hex Assertion'))
        registerFactory('check_response', new CheckFactory('response'))
        registerFactory('check_request', new CheckFactory('request'))
        registerFactory('check_size', new CheckFactory('size'))

        // postprocessors
        registerFactory('jsrpostprocessor', new JSR223PostProcessorFactory('JSR223 PostProcessor'))

        // preprocessors
        registerFactory('jsrpreprocessor', new JSR223PreProcessorFactory('JSR223 PreProcessor'))

        // configs
        registerFactory('headers', new HeadersFactory('Headers'))
        registerFactory('header', new HeaderFactory())
        registerFactory('defaults', new DefaultsFactory('HTTP Request Defaults'))
        registerFactory('csv', new CsvDataFactory('CSV Data Set Config'))
        registerFactory('cookies', new CookiesFactory('HTTP Cookie Manager'))
        registerFactory('cookie', new CookieFactory())
        registerFactory('cache', new CacheFactory('Cache Manager'))
        registerFactory('login', new LoginFactory('Login Config Element'))
        registerFactory('variables', new VariablesFactory('User Defined Variables'))
        registerFactory('variable', new VariableFactory())
        registerFactory('authorizations', new AuthorizationsFactory('HTTP Authorization Manager'))
        registerFactory('authorization', new AuthorizationFactory())
        registerFactory('counter', new CounterFactory('Counter'))
        registerFactory('random', new RandomVariableFactory('Random Variable'))

        // listeners
        registerFactory('summary', new SummaryFactory('Summary Report'))
        registerFactory('aggregate', new AggregateFactory('Aggregate Report'))
        registerFactory('backend', new BackendListenerFactory('Backend Listener'))
        registerFactory('jsrlistener', new JSR223ListenerFactory('JSR223 Listener'))

        registerFactory('insert', new InsertFactory())
    }

    void registerPluginFactory(String type, AbstractFactory factory) {
        registerFactory(type, factory)
    }
}
