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
import net.simonix.dsl.jmeter.factory.common.BodyFactory
import net.simonix.dsl.jmeter.factory.common.ParamFactory
import net.simonix.dsl.jmeter.factory.common.ParamsFactory
import net.simonix.dsl.jmeter.factory.config.DefaultsFactory
import net.simonix.dsl.jmeter.factory.config.http.DefaultsProxyFactory
import net.simonix.dsl.jmeter.factory.config.http.DefaultsResourcesFactory
import net.simonix.dsl.jmeter.factory.config.http.DefaultsSourceFactory
import net.simonix.dsl.jmeter.factory.config.http.DefaultsTimeoutFactory
import net.simonix.dsl.jmeter.model.TestElementNode
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement

@CompileDynamic
class DefaultsHttpFactoryBuilder extends TestFactoryBuilder {

    static List<String> ACCEPTED_KEYWORDS = [
            DslDefinition.DEFAULTS.name
    ]

    DefaultsHttpFactoryBuilder(Map<String, Object> context, Closure closure) {
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

        addFactory(new DefaultsFactory())

        // common
        addFactory(new ParamFactory())
        addFactory(new ParamsFactory())
        addFactory(new BodyFactory())

        // sampler
        addFactory(new DefaultsProxyFactory())
        addFactory(new DefaultsResourcesFactory())
        addFactory(new DefaultsSourceFactory())
        addFactory(new DefaultsTimeoutFactory())
    }

    protected Object postNodeCompletion(Object parent, Object node) {
        super.postNodeCompletion(parent, node)

        if(parent instanceof TestElementNode && parent.testElement instanceof ConfigTestElement) {
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
