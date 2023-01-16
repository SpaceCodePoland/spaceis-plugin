package pl.spaceis.plugin.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.velocity.commands.SpaceIsCommand;
import pl.spaceis.plugin.velocity.config.VelocityConfigLoader;
import pl.spaceis.plugin.velocity.logger.VelocitySpaceIsLogger;
import pl.spaceis.plugin.velocity.task.VelocityCommandsTask;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


@Plugin(
        id = "spaceisplugin",
        name = "SpaceIsPlugin",
        version = "1.1",
        description = "Wykonuj komendy ze swojego sklepu SpaceIs",
        url = "https://spaceis.pl/",
        authors = {"Kamilkime", "robalmeister"}
)
public class VelocitySpaceIsPlugin {
    public final ProxyServer server;
    public final SpaceIsLogger logger;
    private final Path dataDirectory;
    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Inject
    public VelocitySpaceIsPlugin(ProxyServer server, Logger logger, @DataDirectory final Path dataDirectory) {

        this.server = server;
        this.logger = new VelocitySpaceIsLogger(logger);
        this.dataDirectory = dataDirectory;

    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) throws EmptyConfigFieldException {

        final ConfigLoader configLoader = new VelocityConfigLoader(dataDirectory.toFile());
        final Config config = new Config(configLoader);
        server.getScheduler().buildTask(this, new VelocityCommandsTask(httpClient, config, logger, this)).repeat(config.taskInterval.getSeconds() * 20L, TimeUnit.SECONDS).schedule();
        CommandManager commandManager = server.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("spaceis")
                // This will create a new alias for the command "/test"
                // with the same arguments and functionality

                .plugin(this)
                .build();
        commandManager.register(commandMeta, new SpaceIsCommand(config));
    }
}
