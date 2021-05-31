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
import net.simonix.dsl.jmeter.factory.assertion.CheckRequestFactory
import net.simonix.dsl.jmeter.factory.assertion.CheckResponseFactory
import net.simonix.dsl.jmeter.factory.assertion.CheckSizeFactory
import net.simonix.dsl.jmeter.factory.assertion.DurationAssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.JSR223AssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.JsonAssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.MD5HexAssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.ResponseAssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.SizeAssertionFactory
import net.simonix.dsl.jmeter.factory.assertion.XPathAssertionFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcConnectionFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcInitFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcPoolFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcValidationFactory
import net.simonix.dsl.jmeter.factory.extractor.CssSelectorExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.JsonPathExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.RegExExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.XPathExtractorFactory
import net.simonix.dsl.jmeter.factory.postprocessor.JSR223PostProcessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.JSR223PreProcessorFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcAutocommitFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCallableFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcCommitFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcExecuteFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcParameterFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcParametersFactory
import net.simonix.dsl.jmeter.factory.postprocessor.jdbc.JdbcPostprocessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.jdbc.JdbcPreprocessorFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcQueryFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.JdbcRollbackFactory
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
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.jdbc.AbstractJDBCTestElement

@CompileDynamic
class JdbcFactoryBuilder extends TestFactoryBuilder {

    static List<String> ACCEPTED_KEYWORDS = [
            DslDefinition.JDBC.name,
            DslDefinition.JDBC_CONFIG.name,
            DslDefinition.JDBC_REQUEST.name,
            DslDefinition.JDBC_PREPROCESSOR.name,
            DslDefinition.JDBC_POSTPROCESSOR.name
    ]

    JdbcFactoryBuilder(Map<String, Object> context, Closure closure) {
        super()

        // create new context for jdbc tree
        getProxyBuilder().newContext()
        getProxyBuilder().getContext().put(OWNER, closure.getOwner())
        getProxyBuilder().getContext().put(CURRENT_NODE, context.get(CURRENT_NODE))
        getProxyBuilder().getContext().put(PARENT_FACTORY, context.get(PARENT_FACTORY))
        getProxyBuilder().getContext().put(PARENT_NODE, context.get(PARENT_NODE))
        getProxyBuilder().getContext().put(PARENT_CONTEXT, context.get(PARENT_CONTEXT))
        getProxyBuilder().getContext().put(PARENT_NAME, context.get(PARENT_NAME))
        getProxyBuilder().getContext().put(PARENT_BUILDER, context.get(PARENT_BUILDER))
        getProxyBuilder().getContext().put(CURRENT_BUILDER, context.get(CURRENT_BUILDER))
    }

    void registerObjectFactories() {

        // configs
        addFactory(new JdbcConnectionFactory())
        addFactory(new JdbcPoolFactory())
        addFactory(new JdbcInitFactory())
        addFactory(new JdbcValidationFactory())

        // common
        addFactory(new JdbcParametersFactory())
        addFactory(new JdbcParameterFactory())

        // sampler
        addFactory(new JdbcQueryFactory())
        addFactory(new JdbcExecuteFactory())
        addFactory(new JdbcCallableFactory())
        addFactory(new JdbcAutocommitFactory())
        addFactory(new JdbcCommitFactory())
        addFactory(new JdbcRollbackFactory())

        // preprocessor
        addFactory(new JdbcPreprocessorFactory())
        addFactory(new JSR223PreProcessorFactory())

        // postprocessor
        addFactory(new JdbcPostprocessorFactory())
        addFactory(new JSR223PostProcessorFactory())

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
    }

    protected Object postNodeCompletion(Object parent, Object node) {
        super.postNodeCompletion(parent, node)

        if(parent instanceof TestElementNode && parent.testElement instanceof AbstractJDBCTestElement) {
            Factory factory = getCurrentFactory()
            factory.updateOnComplete(parent.testElement, node)
        }

        return node
    }

    protected void setClosureDelegate(Closure closure, Object node) {
        closure.delegate = this
        closure.resolveStrategy = Closure.DELEGATE_ONLY
    }
}
