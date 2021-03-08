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
package net.simonix.dsl.jmeter.factory

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.FragmentTestScript
import net.simonix.dsl.jmeter.model.definition.DefinitionAwareMap
import net.simonix.dsl.jmeter.model.definition.DefinitionProvider
import net.simonix.dsl.jmeter.model.definition.KeywordDefinition
import net.simonix.dsl.jmeter.validation.Validator
import net.simonix.dsl.jmeter.validation.ValidatorProvider
import net.simonix.dsl.jmeter.validation.PropertyValidator
import org.codehaus.groovy.control.CompilerConfiguration

import static net.simonix.dsl.jmeter.utils.ConfigUtils.readValue

/**
 * Abstract class for inserting test fragments.
 */
@CompileDynamic
abstract class AbstractTestElementFragmentFactory extends AbstractFactory implements ValidatorProvider, DefinitionProvider {

    final GroovyShell groovyShell

    final KeywordDefinition definition
    final Validator validator

    protected AbstractTestElementFragmentFactory(KeywordDefinition definition) {
        this.definition = definition

        this.validator = new PropertyValidator(definition.properties, definition.valueIsProperty)

        CompilerConfiguration config = new CompilerConfiguration()
        config.scriptBaseClass = FragmentTestScript.name

        groovyShell = new GroovyShell(this.class.classLoader, config)
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map config) throws InstantiationException, IllegalAccessException {
        Map definitionAwareConfig = new DefinitionAwareMap(config, definition)

        String file = readValue(value, definitionAwareConfig.file)

        URL url = this.class.classLoader.getResource(file)
        return groovyShell.evaluate(url.toURI())
    }

    boolean isLeaf() {
        return definition.leaf
    }

    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map config ) {
        return false
    }
}
