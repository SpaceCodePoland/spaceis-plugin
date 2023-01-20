package pl.spaceis.plugin.bungee;

import net.md_5.bungee.config.Configuration;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeConfigLoader implements ConfigLoader {

    private final ResourceLoader<Configuration> resourceLoader;
    private final String configFileName;
    private Configuration configFile;

    public BungeeConfigLoader(final ResourceLoader<Configuration> resourceLoader, final String configFileName) {
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
        return this.configFile.getBoolean(key);
    }

    @Override
    public String getString(final String key) {
        return this.configFile.getString(key);
    }

}
