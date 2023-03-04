package pl.spaceis.plugin.velocity;

import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class VelocityConfigLoader implements ConfigLoader {

    private final String configFileName;

    public VelocityConfigLoader(final String configFileName) {
        this.configFileName = configFileName;
    }

    @Override
    public void reloadConfig() throws ResourceLoaderException {

    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public String getString(String key) {
        return null;
    }

}
