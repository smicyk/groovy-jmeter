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
package net.simonix.dsl.jmeter.builder

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.assertion.*
import net.simonix.dsl.jmeter.factory.common.*
import net.simonix.dsl.jmeter.factory.config.*
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcConfigFactory
import net.simonix.dsl.jmeter.factory.common.jdbc.JdbcFactory
import net.simonix.dsl.jmeter.factory.controller.*
import net.simonix.dsl.jmeter.factory.controller.execution.*
import net.simonix.dsl.jmeter.factory.extractor.CssSelectorExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.JsonPathExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.RegExExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.XPathExtractorFactory
import net.simonix.dsl.jmeter.factory.group.GroupFactory
import net.simonix.dsl.jmeter.factory.group.PreGroupFactory
import net.simonix.dsl.jmeter.factory.group.PostGroupFactory
import net.simonix.dsl.jmeter.factory.listener.AggregateFactory
import net.simonix.dsl.jmeter.factory.listener.BackendListenerFactory
import net.simonix.dsl.jmeter.factory.listener.JSR223ListenerFactory
import net.simonix.dsl.jmeter.factory.listener.SummaryFactory
import net.simonix.dsl.jmeter.factory.plan.PlanFactory
import net.simonix.dsl.jmeter.factory.postprocessor.JSR223PostProcessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.JSR223PreProcessorFactory
import net.simonix.dsl.jmeter.factory.sampler.*
import net.simonix.dsl.jmeter.factory.postprocessor.jdbc.JdbcPostprocessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.jdbc.JdbcPreprocessorFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRequestFactory
import net.simonix.dsl.jmeter.factory.timer.ConstantThroughputFactory
import net.simonix.dsl.jmeter.factory.timer.ConstantTimerFactory
import net.simonix.dsl.jmeter.factory.timer.GaussianTimerFactory
import net.simonix.dsl.jmeter.factory.timer.JSR223TimerFactory
import net.simonix.dsl.jmeter.factory.timer.PoissonTimerFactory
import net.simonix.dsl.jmeter.factory.timer.PreciseThroughputFactory
import net.simonix.dsl.jmeter.factory.timer.SynchronizingTimerFactory
import net.simonix.dsl.jmeter.factory.timer.ThroughputFactory
import net.simonix.dsl.jmeter.factory.timer.TimerFactory
import net.simonix.dsl.jmeter.factory.timer.UniformTimerFactory
import net.simonix.dsl.jmeter.model.TestElementNode

/**
 * Handles all DSL keywords and builds final {@link net.simonix.dsl.jmeter.model.TestElementNode} tree.
 */
@CompileDynamic
class DefaultFactoryBuilder extends TestFactoryBuilder {

    DefaultFactoryBuilder() {
        super()
    }

    void registerObjectFactories() {
        // plan and group
        addFactory(new PlanFactory())
        addFactory(new GroupFactory())
        addFactory(new PreGroupFactory())
        addFactory(new PostGroupFactory())

        // controllers
        addFactory(new LoopFactory())
        addFactory(new SimpleFactory())
        addFactory(new TransactionFactory())
        addFactory(new CriticalSectionFactory())
        addFactory(new IncludeFactory())
        addFactory(new ForEachFactory())

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
        addFactory(new HttpFactory())
        addFactory(new AjpFactory())
        addFactory(new DebugFactory())
        addFactory(new JSR223SamplerFactory())
        addFactory(new FlowControlActionFactory())
        addFactory(new JdbcRequestFactory())

        // others
        addFactory(new ParamFactory())
        addFactory(new ParamsFactory())
        addFactory(new FileFactory())
        addFactory(new FilesFactory())
        addFactory(new BodyFactory())
        addFactory(new ArgumentFactory())
        addFactory(new ArgumentsFactory())
        addFactory(new InsertFactory())

        // timers
        addFactory(new TimerFactory())
        addFactory(new ConstantTimerFactory())
        addFactory(new UniformTimerFactory())
        addFactory(new GaussianTimerFactory())
        addFactory(new PoissonTimerFactory())
        addFactory(new ThroughputFactory())
        addFactory(new ConstantThroughputFactory())
        addFactory(new PreciseThroughputFactory())
        addFactory(new SynchronizingTimerFactory())
        addFactory(new JSR223TimerFactory())

        // extractors
        addFactory(new RegExExtractorFactory())
        addFactory(new CssSelectorExtractorFactory())
        addFactory(new JsonPathExtractorFactory())
        addFactory(new XPathExtractorFactory())

        // assertions
        addFactory(new JSR223AssertionFactory())
        addFactory(new ResponseAssertionFactory())
        addFactory(new SizeAssertionFactory())
        addFactory(new DurationAssertionFactory())
        addFactory(new XPathAssertionFactory())
        addFactory(new JsonAssertionFactory())
        addFactory(new MD5HexAssertionFactory())
        addFactory(new CheckResponseFactory())
        addFactory(new CheckRequestFactory())
        addFactory(new CheckSizeFactory())

        // postprocessors
        addFactory(new JSR223PostProcessorFactory())
        addFactory(new JdbcPostprocessorFactory())

        // preprocessors
        addFactory(new JSR223PreProcessorFactory())
        addFactory(new JdbcPreprocessorFactory())

        // configs
        addFactory(new HeadersFactory())
        addFactory(new HeaderFactory())
        addFactory(new DefaultsFactory())
        addFactory(new CsvDataFactory())
        addFactory(new CookiesFactory())
        addFactory(new CookieFactory())
        addFactory(new CacheFactory())
        addFactory(new LoginFactory())
        addFactory(new VariablesFactory())
        addFactory(new VariableFactory())
        addFactory(new AuthorizationsFactory())
        addFactory(new AuthorizationFactory())
        addFactory(new DnsFactory())
        addFactory(new DnsHostFactory())
        addFactory(new CounterFactory())
        addFactory(new RandomVariableFactory())
        addFactory(new JdbcFactory())
        addFactory(new JdbcConfigFactory())

        // listeners
        addFactory(new SummaryFactory())
        addFactory(new AggregateFactory())
        addFactory(new BackendListenerFactory())
        addFactory(new JSR223ListenerFactory())
    }

    protected void setClosureDelegate(Closure closure, Object node) {
        if(node instanceof TestElementNode && JdbcFactoryBuilder.ACCEPTED_KEYWORDS.contains(node.name)) {
            Map<String, Object> parentContext = getProxyBuilder().getContext()

            JdbcFactoryBuilder builder = new JdbcFactoryBuilder(parentContext, closure)

            // copy any variables from command line to the child builder
            this.variables.each { entry ->
                builder.setVariable(entry.key as String, entry.value)
            }

            closure.delegate = builder
            closure.resolveStrategy = Closure.DELEGATE_ONLY
        } else {
            closure.delegate = this
            closure.resolveStrategy = Closure.DELEGATE_ONLY
        }
    }
}
