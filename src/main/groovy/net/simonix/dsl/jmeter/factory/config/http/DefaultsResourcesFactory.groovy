package net.simonix.dsl.jmeter.factory.config.http

import groovy.transform.CompileDynamic
import net.simonix.dsl.jmeter.factory.sampler.http.AbstractResourcesFactory
import net.simonix.dsl.jmeter.factory.sampler.http.model.ResourceTestElement
import net.simonix.dsl.jmeter.model.definition.DslDefinition
import org.apache.jmeter.config.ConfigTestElement
import org.apache.jmeter.protocol.http.sampler.HTTPSampler

/**
 * The factory class responsible for building <code>resources</code> element inside defaults element.
 *
 * <pre>
 * // element structure
 * resources (
 *     // Embedded resource
 *     parallel: int [<strong>6</strong>]
 *     urlInclude: string
 *     urlExclude: string
 * )
 * </pre>
 * More details about the parameters are available at <a href="https://jmeter.apache.org/usermanual/component_reference.html#HTTP_Request_Defaults">HTTP Request Defaults</a>
 *
 * @see net.simonix.dsl.jmeter.factory.config.DefaultsFactory DefaultsFactory
 * @see net.simonix.dsl.jmeter.factory.TestElementNodeFactory TestElementNodeFactory
 */
@CompileDynamic
final class DefaultsResourcesFactory extends AbstractResourcesFactory {

    DefaultsResourcesFactory() {
        super(DslDefinition.DEFAULTS_RESOURCES)
    }

    void updateOnComplete(Object parent, Object child) {
        if (parent instanceof ConfigTestElement && child instanceof ResourceTestElement) {
            ConfigTestElement testElement = parent as ConfigTestElement

            testElement.setProperty(HTTPSampler.IMAGE_PARSER, true)
            testElement.setProperty(HTTPSampler.CONCURRENT_DWN, child.downloadParallel)
            testElement.setProperty(HTTPSampler.CONCURRENT_POOL, child.parallel)

            if(child.urlInclude != null && !child.urlInclude.isEmpty()) {
                testElement.setProperty(HTTPSampler.EMBEDDED_URL_RE, child.urlInclude)
            }

            if (child.urlExclude != null && !child.urlExclude.isEmpty()) {
                testElement.setProperty(HTTPSampler.EMBEDDED_URL_EXCLUDE_RE, child.urlExclude)
            }
        }
    }
}
