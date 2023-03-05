package pl.spaceis.plugin.velocity;

import java.io.File;
import java.nio.file.Path;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;
import pl.spaceis.plugin.resource.ResourceLoaderException.Reason;

public class VelocityResourceLoader extends ResourceLoader<ConfigurationNode> {

    public VelocityResourceLoader(final Class<?> loadingClass, final Path dataDirectory) {
        super(loadingClass, dataDirectory);
    }

    @Override
    protected ConfigurationNode loadResource(final Path resourcePath) throws ResourceLoaderException {
        try {
            return YAMLConfigurationLoader.builder().setPath(resourcePath).build().load();
        } catch (final Exception exception) {
            throw new ResourceLoaderException(Reason.FILE_NOT_LOADED, resourcePath.getFileName().toString(), exception);
        }
    }

}
