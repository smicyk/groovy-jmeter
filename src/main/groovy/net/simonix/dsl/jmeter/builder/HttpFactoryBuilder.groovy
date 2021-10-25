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
import net.simonix.dsl.jmeter.factory.common.ArgumentFactory
import net.simonix.dsl.jmeter.factory.common.ArgumentsFactory
import net.simonix.dsl.jmeter.factory.common.BodyFactory
import net.simonix.dsl.jmeter.factory.common.FileFactory
import net.simonix.dsl.jmeter.factory.common.FilesFactory
import net.simonix.dsl.jmeter.factory.common.InsertFactory
import net.simonix.dsl.jmeter.factory.common.ParamFactory
import net.simonix.dsl.jmeter.factory.common.ParamsFactory
import net.simonix.dsl.jmeter.factory.common.jdbc.JdbcFactory
import net.simonix.dsl.jmeter.factory.config.AuthorizationFactory
import net.simonix.dsl.jmeter.factory.config.AuthorizationsFactory
import net.simonix.dsl.jmeter.factory.config.CacheFactory
import net.simonix.dsl.jmeter.factory.config.CookieFactory
import net.simonix.dsl.jmeter.factory.config.CookiesFactory
import net.simonix.dsl.jmeter.factory.config.CounterFactory
import net.simonix.dsl.jmeter.factory.config.CsvDataFactory
import net.simonix.dsl.jmeter.factory.config.DefaultsFactory
import net.simonix.dsl.jmeter.factory.config.DnsFactory
import net.simonix.dsl.jmeter.factory.config.DnsHostFactory
import net.simonix.dsl.jmeter.factory.config.HeaderFactory
import net.simonix.dsl.jmeter.factory.config.HeadersFactory
import net.simonix.dsl.jmeter.factory.config.LoginFactory
import net.simonix.dsl.jmeter.factory.config.RandomVariableFactory
import net.simonix.dsl.jmeter.factory.config.VariableFactory
import net.simonix.dsl.jmeter.factory.config.VariablesFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcConfigFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcConnectionFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcInitFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcPoolFactory
import net.simonix.dsl.jmeter.factory.config.jdbc.JdbcValidationFactory
import net.simonix.dsl.jmeter.factory.extractor.CssSelectorExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.JsonPathExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.RegExExtractorFactory
import net.simonix.dsl.jmeter.factory.extractor.XPathExtractorFactory
import net.simonix.dsl.jmeter.factory.postprocessor.JSR223PostProcessorFactory
import net.simonix.dsl.jmeter.factory.postprocessor.jdbc.JdbcPostprocessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.JSR223PreProcessorFactory
import net.simonix.dsl.jmeter.factory.preprocessor.jdbc.JdbcPreprocessorFactory
import net.simonix.dsl.jmeter.factory.sampler.AjpFactory
import net.simonix.dsl.jmeter.factory.sampler.HttpFactory
import net.simonix.dsl.jmeter.factory.sampler.http.HttpProxyFactory
import net.simonix.dsl.jmeter.factory.sampler.http.HttpResourceFactory
import net.simonix.dsl.jmeter.factory.sampler.http.HttpSourceAddressFactory
import net.simonix.dsl.jmeter.factory.sampler.http.HttpTimeoutFactory
import net.simonix.dsl.jmeter.factory.sampler.jdbc.*
import net.simonix.dsl.jmeter.factory.timer.*
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase
import org.apache.jmeter.protocol.jdbc.AbstractJDBCTestElement

@CompileDynamic
class HttpFactoryBuilder extends TestFactoryBuilder {

    static List<String> ACCEPTED_KEYWORDS = [
            DslDefinition.HTTP.name,
            DslDefinition.AJP.name,
            DslDefinition.DEFAULTS.name
    ]

    HttpFactoryBuilder(Map<String, Object> context, Closure closure) {
        super()

        // create new context for http tree
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
        addFactory(new HeadersFactory())
        addFactory(new HeaderFactory())
        addFactory(new DefaultsFactory())
        addFactory(new CookiesFactory())
        addFactory(new CookieFactory())
        addFactory(new CacheFactory())
        addFactory(new LoginFactory())
        addFactory(new AuthorizationsFactory())
        addFactory(new AuthorizationFactory())

        // common
        addFactory(new ParamFactory())
        addFactory(new ParamsFactory())
        addFactory(new FileFactory())
        addFactory(new FilesFactory())
        addFactory(new BodyFactory())

        // sampler
        addFactory(new HttpFactory())
        addFactory(new HttpProxyFactory())
        addFactory(new HttpResourceFactory())
        addFactory(new HttpSourceAddressFactory())
        addFactory(new HttpTimeoutFactory())
        addFactory(new AjpFactory())

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

        if(parent instanceof TestElementNode && parent.testElement instanceof HTTPSamplerBase) {
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
