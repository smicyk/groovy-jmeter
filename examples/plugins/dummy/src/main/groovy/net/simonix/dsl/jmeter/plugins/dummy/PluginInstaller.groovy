package net.simonix.dsl.jmeter.plugins.dummy

import groovy.transform.CompileStatic
import net.simonix.dsl.jmeter.plugin.PluginContainer
import net.simonix.dsl.jmeter.plugin.PluginProvider
import net.simonix.dsl.jmeter.plugins.dummy.sampler.DummySamplerFactory

/**
 * Plugin specific installer.
 * <p>
 * Add plugins to container.
 */
@CompileStatic
class PluginInstaller implements PluginProvider {

    @Override
    void addPlugins(PluginContainer container) {
        container.add(new DummySamplerFactory())
    }
}
