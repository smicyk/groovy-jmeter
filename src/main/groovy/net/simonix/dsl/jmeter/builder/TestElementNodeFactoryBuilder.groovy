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
import net.simonix.dsl.jmeter.model.DslDefinition
import net.simonix.dsl.jmeter.validation.ValidationResult
import net.simonix.dsl.jmeter.validation.Validator
import net.simonix.dsl.jmeter.validation.ValidatorProvider
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

    protected void preInstantiate(Object name, Map attributes, Object value) {
        super.preInstantiate(name, attributes, value)

        ValidatorProvider provider = getCurrentFactory() as ValidatorProvider

        Validator validator = provider.getValidator()
        ValidationResult result = validator.validate(name, value, attributes)

        if(!result.valid) {
            throw new IllegalArgumentException(result.message)
        }
    }

    void registerObjectFactories() {
        // plan and group
        registerFactory(DslDefinition.PLAN, new PlanFactory('Test Plan'))
        registerFactory(DslDefinition.GROUP, new GroupFactory('User Group'))

        // controllers
        registerFactory(DslDefinition.LOOP, new LoopFactory('Loop Controller'))
        registerFactory(DslDefinition.SIMPLE, new SimpleFactory('Simple Controller'))
        registerFactory(DslDefinition.TRANSACTION, new TransactionFactory('Transaction Controller'))
        registerFactory(DslDefinition.SECTION, new CriticalSectionFactory('Critical Section Controller'))
        registerFactory(DslDefinition.INCLUDE, new IncludeFactory('Include Controller'))
        registerFactory(DslDefinition.FOR_EACH, new ForEachFactory('ForEach Controller'))

        registerFactory(DslDefinition.EXECUTE, new ExecuteFactory())

        registerFactory(DslDefinition.EXECUTE_IF, new IfControllerFactory())
        registerFactory(DslDefinition.EXECUTE_WHILE, new WhileControllerFactory())
        registerFactory(DslDefinition.EXECUTE_ONCE, new OnceControllerFactory())
        registerFactory(DslDefinition.EXECUTE_INTERLEAVE, new InterleaveControllerFactory())
        registerFactory(DslDefinition.EXECUTE_RANDOM, new RandomControllerFactory())
        registerFactory(DslDefinition.EXECUTE_ORDER, new RandomOrderControllerFactory())
        registerFactory(DslDefinition.EXECUTE_PERCENT, new PercentControllerFactory())
        registerFactory(DslDefinition.EXECUTE_TOTAL, new TotalControllerFactory())
        registerFactory(DslDefinition.EXECUTE_RUNTIME, new RuntimeControllerFactory())
        registerFactory(DslDefinition.EXECUTE_SWITCH, new SwitchControllerFactory())

        registerFactory(DslDefinition.EXEC_IF, new IfControllerFactory())
        registerFactory(DslDefinition.EXEC_WHILE, new WhileControllerFactory())
        registerFactory(DslDefinition.EXEC_ONCE, new OnceControllerFactory())
        registerFactory(DslDefinition.EXEC_INTERLEAVE, new InterleaveControllerFactory())
        registerFactory(DslDefinition.EXEC_RANDOM, new RandomControllerFactory())
        registerFactory(DslDefinition.EXEC_ORDER, new RandomOrderControllerFactory())
        registerFactory(DslDefinition.EXEC_PERCENT, new PercentControllerFactory())
        registerFactory(DslDefinition.EXEC_TOTAL, new TotalControllerFactory())
        registerFactory(DslDefinition.EXEC_RUNTIME, new RuntimeControllerFactory())
        registerFactory(DslDefinition.EXEC_SWITCH, new SwitchControllerFactory())

        // samplers
        registerFactory(DslDefinition.HTTP, new HttpFactory('HTTP Sampler'))
        registerFactory(DslDefinition.AJP, new AjpFactory('AJP Sampler'))
        registerFactory(DslDefinition.DEBUG, new DebugFactory('Debug Sampler'))
        registerFactory(DslDefinition.JSR223_SAMPLER, new JSR223SamplerFactory('JSR223 Sampler'))
        registerFactory(DslDefinition.FLOW, new FlowControlActionFactory('Flow Control Action'))

        // others
        registerFactory(DslDefinition.PARAM, new ParamFactory())
        registerFactory(DslDefinition.PARAMS, new ParamsFactory())
        registerFactory(DslDefinition.BODY, new BodyFactory())
        registerFactory(DslDefinition.INSERT, new InsertFactory())
        registerFactory(DslDefinition.ARGUMENT, new ArgumentFactory())
        registerFactory(DslDefinition.ARGUMENTS, new ArgumentsFactory())

        // timers
        registerFactory(DslDefinition.CONSTANT_TIMER, new ConstantTimerFactory('Constant Timer'))
        registerFactory(DslDefinition.UNIFORM_TIMER, new UniformRandomTimeFactory('Uniform Random Timer'))
        registerFactory(DslDefinition.JSR223_TIMER, new JSR223TimerFactory('JSR223 Timer'))

        // extractors
        registerFactory(DslDefinition.REGEX_EXTRACTOR, new RegExExtractorFactory('Regular Expression Extractor'))
        registerFactory(DslDefinition.CSS_EXTRACTOR, new CssSelectorExtractorFactory('CSS Selector Extractor'))
        registerFactory(DslDefinition.JSON_EXTRACTOR, new JsonPathExtractorFactory('Json Path Extractor'))

        // assertions
        registerFactory(DslDefinition.JSR223_ASSERTION, new JSR223AssertionFactory('JSR223 Assertion'))
        registerFactory(DslDefinition.ASSERT_RESPONSE, new ResponseAssertionFactory('Response Assertion'))
        registerFactory(DslDefinition.ASSERT_SIZE, new SizeAssertionFactory('Size Assertion'))
        registerFactory(DslDefinition.ASSERT_DURATION, new DurationAssertionFactory('Duration Assertion'))
        registerFactory(DslDefinition.ASSERT_XPATH, new XPathAssertionFactory('XPath Assertion'))
        registerFactory(DslDefinition.ASSERT_JSON, new JsonAssertionFactory('Json Path Assertion'))
        registerFactory(DslDefinition.ASSERT_MD5HEX, new MD5HexAssertionFactory('MD5Hex Assertion'))
        registerFactory(DslDefinition.CHECK_RESPONSE, new CheckFactory('response'))
        registerFactory(DslDefinition.CHECK_REQUEST, new CheckFactory('request'))
        registerFactory(DslDefinition.CHECK_SIZE, new CheckFactory('size'))

        // postprocessors
        registerFactory(DslDefinition.JSR223_POSTPROCESSOR, new JSR223PostProcessorFactory('JSR223 PostProcessor'))

        // preprocessors
        registerFactory(DslDefinition.JSR223_PREPROCESSOR, new JSR223PreProcessorFactory('JSR223 PreProcessor'))

        // configs
        registerFactory(DslDefinition.HEADERS, new HeadersFactory('Headers'))
        registerFactory(DslDefinition.HEADER, new HeaderFactory())
        registerFactory(DslDefinition.DEFAULTS, new DefaultsFactory('HTTP Request Defaults'))
        registerFactory(DslDefinition.CSV_DATA, new CsvDataFactory('CSV Data Set Config'))
        registerFactory(DslDefinition.COOKIES, new CookiesFactory('HTTP Cookie Manager'))
        registerFactory(DslDefinition.COOKIE, new CookieFactory())
        registerFactory(DslDefinition.CACHE, new CacheFactory('Cache Manager'))
        registerFactory(DslDefinition.LOGIN, new LoginFactory('Login Config Element'))
        registerFactory(DslDefinition.VARIABLES, new VariablesFactory('User Defined Variables'))
        registerFactory(DslDefinition.VARIABLE, new VariableFactory())
        registerFactory(DslDefinition.AUTHORIZATIONS, new AuthorizationsFactory('HTTP Authorization Manager'))
        registerFactory(DslDefinition.AUTHORIZATION, new AuthorizationFactory())
        registerFactory(DslDefinition.COUNTER, new CounterFactory('Counter'))
        registerFactory(DslDefinition.RANDOM_VARIABLE, new RandomVariableFactory('Random Variable'))

        // listeners
        registerFactory(DslDefinition.SUMMARY, new SummaryFactory('Summary Report'))
        registerFactory(DslDefinition.AGGREGATE, new AggregateFactory('Aggregate Report'))
        registerFactory(DslDefinition.BACKEND, new BackendListenerFactory('Backend Listener'))
        registerFactory(DslDefinition.JSR223_LISTENER, new JSR223ListenerFactory('JSR223 Listener'))
    }

    void registerPluginFactory(String type, AbstractFactory factory) {
        registerFactory(type, factory)
    }
}
