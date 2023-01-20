package pl.spaceis.plugin.bungee;

import net.md_5.bungee.api.ProxyServer;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.command.CommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class BungeeCommandsTask extends CommandsTask {

    private final ProxyServer proxy;

    public BungeeCommandsTask(final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger) {
        super(httpClient, config, logger);
        this.proxy = ProxyServer.getInstance();
    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return this.proxy.getPlayer(playerName) != null;
    }

    @Override
    public void executeCommand(final String command) {
        this.proxy.getPluginManager().dispatchCommand(this.proxy.getConsole(), command);
    }

}
