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
import net.simonix.dsl.jmeter.model.ValidationException
import net.simonix.dsl.jmeter.model.definition.DefinitionProvider
import net.simonix.dsl.jmeter.validation.ValidationResult
import net.simonix.dsl.jmeter.validation.Validator
import net.simonix.dsl.jmeter.validation.ValidatorProvider

/**
 * Base factory builder.
 */
@CompileDynamic
abstract class TestFactoryBuilder extends FactoryBuilderSupport {

    Set<String> validKeywords = new HashSet<>()

    TestFactoryBuilder() {
        super(false)

        this.methodMissingDelegate = { name, config ->
            if (!validKeywords.contains(name)) {
                throw new ValidationException("The keyword '${name}' is not valid. Did you misspell any of valid keywords ${validKeywords}?")
            }
        }

        registerObjectFactories()
    }

    void addFactory(AbstractFactory factory) {
        String name = getKeywordDefinitionName(factory as DefinitionProvider)

        validKeywords.add(name)

        registerFactory(name, factory)
    }

    abstract void registerObjectFactories()

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
