package net.simonix.dsl.jmeter.plugin

/**
 * Keeps factories from plugin provider
 */
class PluginContainer {

    private List<AbstractFactory> factories = []

    void add(AbstractFactory factory) {
        this.factories.add(factory)
    }

    List<AbstractFactory> getPlugins() {
        return this.factories;
    }
}
