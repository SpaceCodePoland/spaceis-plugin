package pl.spaceis.plugin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import java.nio.file.Path;
import net.kyori.adventure.text.Component;
import ninja.leaping.configurate.ConfigurationNode;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

@Plugin(
        id = "spaceis-plugin",
        name = "SpaceIsPlugin",
        version = "1.2",
        description = "Wykonuj komendy ze swojego sklepu SpaceIs",
        url = "https://spaceis.pl/",
        authors = "SpaceIs-plugin Contributors"
)
public class VelocitySpaceIsPlugin {

    private final ProxyServer server;
    private final Logger velocityLogger;
    private final Path dataDirectory;

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Inject
    public VelocitySpaceIsPlugin(final ProxyServer server, final Logger velocityLogger, @DataDirectory final Path dataDirectory) {
        this.server = server;
        this.velocityLogger = velocityLogger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onEnable(final ProxyInitializeEvent event) {
        final SpaceIsLogger logger = new VelocitySpaceIsLogger(this.velocityLogger);
        try {
            final ResourceLoader<ConfigurationNode> resourceLoader = new VelocityResourceLoader(this.getClass(), this.dataDirectory);
            final ConfigLoader configLoader = new VelocityConfigLoader(resourceLoader, "config.yml");
            final Config config = new Config(configLoader);
            final Messages<Component> messages = new VelocityMessages();

            this.server.getScheduler().buildTask(this, new VelocityCommandsTask(this.httpClient, config, logger, this.server))
                    .repeat(config.taskInterval)
                    .schedule();

            this.server.getCommandManager().register("spaceis", new VelocitySpaceIsCommand(config, messages));
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Subscribe
    public void onDisable(final ProxyShutdownEvent event) {
        this.httpClient.dispatcher().executorService().shutdown();
    }

}
