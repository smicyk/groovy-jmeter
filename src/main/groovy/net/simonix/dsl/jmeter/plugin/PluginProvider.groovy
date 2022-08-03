package net.simonix.dsl.jmeter.plugin

/**
 * Plugin must implement this interface to add new factories to plugin container
 */
interface PluginProvider {

    void addPlugins(PluginContainer container)

}