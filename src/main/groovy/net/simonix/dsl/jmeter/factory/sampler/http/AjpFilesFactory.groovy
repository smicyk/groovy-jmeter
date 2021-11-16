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
package net.simonix.dsl.jmeter.factory.sampler.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.common.FilesFactory
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.protocol.http.sampler.AjpSampler
import org.apache.jmeter.protocol.http.util.HTTPFileArgs

/**
 * Builds files for HTTP sampler. It is used with conjunction with <code>ajp</code> elements.
 *
 * <pre>
 * // structure
 * files {
 *   {@link net.simonix.dsl.jmeter.factory.common.FileFactory file}
 * }
 * // example usage
 * start {
 *     plan {
 *         ajp {
 *             files {
 *                 file 'file.txt', name: 'fileParam', type: 'text/plain'
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * @see net.simonix.dsl.jmeter.factory.sampler.http.AjpFileFactory AjpFileFactory
 */
@CompileDynamic
final class AjpFilesFactory extends FilesFactory {

    AjpFilesFactory() {
        super(DslDefinition.AJP_FILES)
    }

    void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object files) {
        if (files instanceof HTTPFileArgs && parent.testElement instanceof AjpSampler) {
            parent.testElement.setHTTPFiles(files.asArray())
        }
    }
}
