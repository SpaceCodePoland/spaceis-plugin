package pl.spaceis.plugin.velocity;

import ninja.leaping.configurate.ConfigurationNode;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class VelocityConfigLoader implements ConfigLoader {

    private final ResourceLoader<ConfigurationNode> resourceLoader;
    private final String configFileName;
    private ConfigurationNode configFile;

    public VelocityConfigLoader(final ResourceLoader<ConfigurationNode> resourceLoader, final String configFileName) {
        this.resourceLoader = resourceLoader;
        this.configFileName = configFileName;
    }

    @Override
    public void reloadConfig() throws ResourceLoaderException {
        this.resourceLoader.saveDefault(this.configFileName);
        this.configFile = this.resourceLoader.load(this.configFileName);
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.configFile.getNode(key).getBoolean();
    }

    @Override
    public String getString(final String key) {
        return this.configFile.getNode(key).getString();
    }

}
