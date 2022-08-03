package net.simonix.dsl.jmeter.plugin

import groovy.transform.CompileDynamic


@Singleton
@CompileDynamic
final class PluginManager {

    final Map<String, PluginContainer> PLUGINS = [:]

    static void updatePluginConfig(Map config) {
        if (!PluginManager.instance.PLUGINS.empty) {
            if (!config.plugins) {
                config.plugins = []
            }

            PluginManager.instance.PLUGINS.each { it ->
                config.plugins.addAll(it.value.plugins)
            }
        }
    }

    void id(String id) {
        Class installerClass = this.class.classLoader.loadClass("${id}.PluginInstaller")

        PluginProvider installer = installerClass.getDeclaredConstructor().newInstance() as PluginProvider

        PluginContainer container = new PluginContainer()
        installer.addPlugins(container)

        installPlugin(id, container)
    }

    private void installPlugin(String id, PluginContainer container) {
        PLUGINS.put(id, container)
    }
}
