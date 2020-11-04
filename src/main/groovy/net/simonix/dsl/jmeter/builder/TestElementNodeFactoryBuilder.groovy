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

import groovy.transform.CompileDynamic
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
import net.simonix.dsl.jmeter.model.ValidationException
import net.simonix.dsl.jmeter.validation.ValidationResult
import net.simonix.dsl.jmeter.validation.Validator
import net.simonix.dsl.jmeter.validation.ValidatorProvider

/**
 * Handles all DSL keywords and builds final {@link net.simonix.dsl.jmeter.model.TestElementNode} tree.
 */
@CompileDynamic
class TestElementNodeFactoryBuilder extends FactoryBuilderSupport {

    TestElementNodeFactoryBuilder(boolean init = true) {
        super(init)

        this.methodMissingDelegate = { name, config ->
            if (!DslDefinition.VALID_KEYWORDS.contains(name)) {
                throw new ValidationException("The keyword '${name}' is not valid. Did you misspell any of valid keywords ${DslDefinition.VALID_KEYWORDS}?")
            }
        }
    }

    void registerObjectFactories() {
        // plan and group
        registerFactory(DslDefinition.PLAN.name, new PlanFactory('Test Plan'))
        registerFactory(DslDefinition.GROUP.name, new GroupFactory('User Group'))

        // controllers
        registerFactory(DslDefinition.LOOP.name, new LoopFactory('Loop Controller'))
        registerFactory(DslDefinition.SIMPLE.name, new SimpleFactory('Simple Controller'))
        registerFactory(DslDefinition.TRANSACTION.name, new TransactionFactory('Transaction Controller'))
        registerFactory(DslDefinition.SECTION.name, new CriticalSectionFactory('Critical Section Controller'))
        registerFactory(DslDefinition.INCLUDE.name, new IncludeFactory('Include Controller'))
        registerFactory(DslDefinition.FOR_EACH.name, new ForEachFactory('ForEach Controller'))

        registerFactory(DslDefinition.EXECUTE.name, new ExecuteFactory())

        registerFactory(DslDefinition.EXECUTE_IF.name, new IfControllerFactory())
        registerFactory(DslDefinition.EXECUTE_WHILE.name, new WhileControllerFactory())
        registerFactory(DslDefinition.EXECUTE_ONCE.name, new OnceControllerFactory())
        registerFactory(DslDefinition.EXECUTE_INTERLEAVE.name, new InterleaveControllerFactory())
        registerFactory(DslDefinition.EXECUTE_RANDOM.name, new RandomControllerFactory())
        registerFactory(DslDefinition.EXECUTE_ORDER.name, new RandomOrderControllerFactory())
        registerFactory(DslDefinition.EXECUTE_PERCENT.name, new PercentControllerFactory())
        registerFactory(DslDefinition.EXECUTE_TOTAL.name, new TotalControllerFactory())
        registerFactory(DslDefinition.EXECUTE_RUNTIME.name, new RuntimeControllerFactory())
        registerFactory(DslDefinition.EXECUTE_SWITCH.name, new SwitchControllerFactory())

        registerFactory(DslDefinition.EXEC_IF.name, new IfControllerFactory())
        registerFactory(DslDefinition.EXEC_WHILE.name, new WhileControllerFactory())
        registerFactory(DslDefinition.EXEC_ONCE.name, new OnceControllerFactory())
        registerFactory(DslDefinition.EXEC_INTERLEAVE.name, new InterleaveControllerFactory())
        registerFactory(DslDefinition.EXEC_RANDOM.name, new RandomControllerFactory())
        registerFactory(DslDefinition.EXEC_ORDER.name, new RandomOrderControllerFactory())
        registerFactory(DslDefinition.EXEC_PERCENT.name, new PercentControllerFactory())
        registerFactory(DslDefinition.EXEC_TOTAL.name, new TotalControllerFactory())
        registerFactory(DslDefinition.EXEC_RUNTIME.name, new RuntimeControllerFactory())
        registerFactory(DslDefinition.EXEC_SWITCH.name, new SwitchControllerFactory())

        // samplers
        registerFactory(DslDefinition.HTTP.name, new HttpFactory('HTTP Sampler'))
        registerFactory(DslDefinition.AJP.name, new AjpFactory('AJP Sampler'))
        registerFactory(DslDefinition.DEBUG.name, new DebugFactory('Debug Sampler'))
        registerFactory(DslDefinition.JSR223_SAMPLER.name, new JSR223SamplerFactory('JSR223 Sampler'))
        registerFactory(DslDefinition.FLOW.name, new FlowControlActionFactory('Flow Control Action'))

        // others
        registerFactory(DslDefinition.PARAM.name, new ParamFactory())
        registerFactory(DslDefinition.PARAMS.name, new ParamsFactory())
        registerFactory(DslDefinition.BODY.name, new BodyFactory())
        registerFactory(DslDefinition.INSERT.name, new InsertFactory())
        registerFactory(DslDefinition.ARGUMENT.name, new ArgumentFactory())
        registerFactory(DslDefinition.ARGUMENTS.name, new ArgumentsFactory())

        // timers
        registerFactory(DslDefinition.CONSTANT_TIMER.name, new ConstantTimerFactory('Constant Timer'))
        registerFactory(DslDefinition.UNIFORM_TIMER.name, new UniformRandomTimeFactory('Uniform Random Timer'))
        registerFactory(DslDefinition.JSR223_TIMER.name, new JSR223TimerFactory('JSR223 Timer'))

        // extractors
        registerFactory(DslDefinition.REGEX_EXTRACTOR.name, new RegExExtractorFactory('Regular Expression Extractor'))
        registerFactory(DslDefinition.CSS_EXTRACTOR.name, new CssSelectorExtractorFactory('CSS Selector Extractor'))
        registerFactory(DslDefinition.JSON_EXTRACTOR.name, new JsonPathExtractorFactory('Json Path Extractor'))

        // assertions
        registerFactory(DslDefinition.JSR223_ASSERTION.name, new JSR223AssertionFactory('JSR223 Assertion'))
        registerFactory(DslDefinition.ASSERT_RESPONSE.name, new ResponseAssertionFactory('Response Assertion'))
        registerFactory(DslDefinition.ASSERT_SIZE.name, new SizeAssertionFactory('Size Assertion'))
        registerFactory(DslDefinition.ASSERT_DURATION.name, new DurationAssertionFactory('Duration Assertion'))
        registerFactory(DslDefinition.ASSERT_XPATH.name, new XPathAssertionFactory('XPath Assertion'))
        registerFactory(DslDefinition.ASSERT_JSON.name, new JsonAssertionFactory('Json Path Assertion'))
        registerFactory(DslDefinition.ASSERT_MD5HEX.name, new MD5HexAssertionFactory('MD5Hex Assertion'))
        registerFactory(DslDefinition.CHECK_RESPONSE.name, new CheckFactory('response'))
        registerFactory(DslDefinition.CHECK_REQUEST.name, new CheckFactory('request'))
        registerFactory(DslDefinition.CHECK_SIZE.name, new CheckFactory('size'))

        // postprocessors
        registerFactory(DslDefinition.JSR223_POSTPROCESSOR.name, new JSR223PostProcessorFactory('JSR223 PostProcessor'))

        // preprocessors
        registerFactory(DslDefinition.JSR223_PREPROCESSOR.name, new JSR223PreProcessorFactory('JSR223 PreProcessor'))

        // configs
        registerFactory(DslDefinition.HEADERS.name, new HeadersFactory('Headers'))
        registerFactory(DslDefinition.HEADER.name, new HeaderFactory())
        registerFactory(DslDefinition.DEFAULTS.name, new DefaultsFactory('HTTP Request Defaults'))
        registerFactory(DslDefinition.CSV_DATA.name, new CsvDataFactory('CSV Data Set Config'))
        registerFactory(DslDefinition.COOKIES.name, new CookiesFactory('HTTP Cookie Manager'))
        registerFactory(DslDefinition.COOKIE.name, new CookieFactory())
        registerFactory(DslDefinition.CACHE.name, new CacheFactory('Cache Manager'))
        registerFactory(DslDefinition.LOGIN.name, new LoginFactory('Login Config Element'))
        registerFactory(DslDefinition.VARIABLES.name, new VariablesFactory('User Defined Variables'))
        registerFactory(DslDefinition.VARIABLE.name, new VariableFactory())
        registerFactory(DslDefinition.AUTHORIZATIONS.name, new AuthorizationsFactory('HTTP Authorization Manager'))
        registerFactory(DslDefinition.AUTHORIZATION.name, new AuthorizationFactory())
        registerFactory(DslDefinition.COUNTER.name, new CounterFactory('Counter'))
        registerFactory(DslDefinition.RANDOM_VARIABLE.name, new RandomVariableFactory('Random Variable'))

        // listeners
        registerFactory(DslDefinition.SUMMARY.name, new SummaryFactory('Summary Report'))
        registerFactory(DslDefinition.AGGREGATE.name, new AggregateFactory('Aggregate Report'))
        registerFactory(DslDefinition.BACKEND.name, new BackendListenerFactory('Backend Listener'))
        registerFactory(DslDefinition.JSR223_LISTENER.name, new JSR223ListenerFactory('JSR223 Listener'))
    }

    void registerPluginFactory(String type, AbstractFactory factory) {
        registerFactory(type, factory)
    }

    protected void preInstantiate(Object name, Map attributes, Object value) {
        super.preInstantiate(name, attributes, value)

        ValidatorProvider provider = getCurrentFactory() as ValidatorProvider

        Validator validator = provider.getValidator()
        ValidationResult result = validator.validate(name, value, attributes)

        if (!result.valid) {
            throw new ValidationException(result.message)
        }
    }
}
