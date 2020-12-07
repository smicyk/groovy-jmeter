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
import net.simonix.dsl.jmeter.model.DefinitionProvider
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

    void addFactory(AbstractFactory factory) {
        String name = getKeywordDefinitionName(factory as DefinitionProvider)

        registerFactory(name, factory)
    }

    void registerObjectFactories() {
        // plan and group
        addFactory(new PlanFactory('Test Plan'))
        addFactory(new GroupFactory('User Group'))

        // controllers
        addFactory(new LoopFactory('Loop Controller'))
        addFactory(new SimpleFactory('Simple Controller'))
        addFactory(new TransactionFactory('Transaction Controller'))
        addFactory(new CriticalSectionFactory('Critical Section Controller'))
        addFactory(new IncludeFactory('Include Controller'))
        addFactory(new ForEachFactory('ForEach Controller'))

        addFactory(new ExecuteFactory())

        addFactory(new IfControllerFactory())
        addFactory(new WhileControllerFactory())
        addFactory(new OnceControllerFactory())
        addFactory(new InterleaveControllerFactory())
        addFactory(new RandomControllerFactory())
        addFactory(new RandomOrderControllerFactory())
        addFactory(new PercentControllerFactory())
        addFactory(new TotalControllerFactory())
        addFactory(new RuntimeControllerFactory())
        addFactory(new SwitchControllerFactory())

        // samplers
        addFactory(new HttpFactory('HTTP Sampler'))
        addFactory(new AjpFactory('AJP Sampler'))
        addFactory(new DebugFactory('Debug Sampler'))
        addFactory(new JSR223SamplerFactory('JSR223 Sampler'))
        addFactory(new FlowControlActionFactory('Flow Control Action'))

        // others
        addFactory(new ParamFactory())
        addFactory(new ParamsFactory())
        addFactory(new BodyFactory())
        addFactory(new InsertFactory())
        addFactory(new ArgumentFactory())
        addFactory(new ArgumentsFactory())

        // timers
        addFactory(new ConstantTimerFactory('Constant Timer'))
        addFactory(new UniformRandomTimeFactory('Uniform Random Timer'))
        addFactory(new JSR223TimerFactory('JSR223 Timer'))

        // extractors
        addFactory(new RegExExtractorFactory('Regular Expression Extractor'))
        addFactory(new CssSelectorExtractorFactory('CSS Selector Extractor'))
        addFactory(new JsonPathExtractorFactory('Json Path Extractor'))

        // assertions
        addFactory(new JSR223AssertionFactory('JSR223 Assertion'))
        addFactory(new ResponseAssertionFactory('Response Assertion'))
        addFactory(new SizeAssertionFactory('Size Assertion'))
        addFactory(new DurationAssertionFactory('Duration Assertion'))
        addFactory(new XPathAssertionFactory('XPath Assertion'))
        addFactory(new JsonAssertionFactory('Json Path Assertion'))
        addFactory(new MD5HexAssertionFactory('MD5Hex Assertion'))
        addFactory(new CheckResponseFactory())
        addFactory(new CheckRequestFactory())
        addFactory(new CheckSizeFactory())

        // postprocessors
        addFactory(new JSR223PostProcessorFactory('JSR223 PostProcessor'))

        // preprocessors
        addFactory(new JSR223PreProcessorFactory('JSR223 PreProcessor'))

        // configs
        addFactory(new HeadersFactory('Headers'))
        addFactory(new HeaderFactory())
        addFactory(new DefaultsFactory('HTTP Request Defaults'))
        addFactory(new CsvDataFactory('CSV Data Set Config'))
        addFactory(new CookiesFactory('HTTP Cookie Manager'))
        addFactory(new CookieFactory())
        addFactory(new CacheFactory('Cache Manager'))
        addFactory(new LoginFactory('Login Config Element'))
        addFactory(new VariablesFactory('User Defined Variables'))
        addFactory(new VariableFactory())
        addFactory(new AuthorizationsFactory('HTTP Authorization Manager'))
        addFactory(new AuthorizationFactory())
        addFactory(new CounterFactory('Counter'))
        addFactory(new RandomVariableFactory('Random Variable'))

        // listeners
        addFactory(new SummaryFactory('Summary Report'))
        addFactory(new AggregateFactory('Aggregate Report'))
        addFactory(new BackendListenerFactory('Backend Listener'))
        addFactory(new JSR223ListenerFactory('JSR223 Listener'))
    }

    void registerPluginFactory(AbstractFactory factory) {
        addFactory(factory)
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

    private String getKeywordDefinitionName(DefinitionProvider provider) {
        return provider.definition.name
    }
}
