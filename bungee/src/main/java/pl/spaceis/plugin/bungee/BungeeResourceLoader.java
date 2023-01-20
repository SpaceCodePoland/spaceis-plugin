package pl.spaceis.plugin.bungee;

import java.io.File;
import java.nio.file.Path;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;
import pl.spaceis.plugin.resource.ResourceLoaderException.Reason;

public class BungeeResourceLoader extends ResourceLoader<Configuration> {

    public BungeeResourceLoader(final Class<?> loadingClass, final File dataFolder) {
        super(loadingClass, dataFolder.toPath());
    }

    @Override
    public Configuration loadResource(final Path resourcePath) throws ResourceLoaderException {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(resourcePath.toFile());
        } catch (final Exception exception) {
            throw new ResourceLoaderException(Reason.FILE_NOT_LOADED, resourcePath.getFileName().toString(), exception);
        }
    }

}