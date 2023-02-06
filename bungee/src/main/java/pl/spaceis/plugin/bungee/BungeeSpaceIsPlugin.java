package pl.spaceis.plugin.bungee;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeSpaceIsPlugin extends Plugin {

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Override
    public void onEnable() {
        final ResourceLoader<Configuration> resourceLoader = new BungeeResourceLoader(this.getClass(), this.getDataFolder());
        try {
            final SpaceIsLogger logger = new BungeeSpaceIsLogger(this.getLogger());
            final ConfigLoader configLoader = new BungeeConfigLoader(resourceLoader, "config.yml");
            final Config config = new Config(configLoader);
            final Messages<BaseComponent> messages = new BungeeMessages();

            ProxyServer.getInstance().getScheduler().schedule(
                    this,
                    new BungeeCommandsTask(this.httpClient, config, logger),
                    0L,
                    config.taskInterval.getSeconds(),
                    TimeUnit.SECONDS
            );

            ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeSpaceIsCommand(config, messages));
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            this.getLogger().severe(exception.getMessage());
            ProxyServer.getInstance().getScheduler().cancel(this);
        }
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getScheduler().cancel(this);
        this.httpClient.dispatcher().executorService().shutdown();
    }

}
