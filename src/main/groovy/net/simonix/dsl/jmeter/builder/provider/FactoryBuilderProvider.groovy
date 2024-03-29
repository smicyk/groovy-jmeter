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
package net.simonix.dsl.jmeter.builder.provider

import net.simonix.dsl.jmeter.builder.TestFactoryBuilder

/**
 * Provides proper {@link TestFactoryBuilder} for accepted keyword name.
 *
 * This provider is only used by child builders in the {@link net.simonix.dsl.jmeter.builder.DefaultFactoryBuilder}
 */
interface FactoryBuilderProvider {

    boolean accepts(String name)

    TestFactoryBuilder create(Map<String, Object> context, Closure closure)
}
