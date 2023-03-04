package pl.spaceis.plugin.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.command.CommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class VelocityCommandsTask extends CommandsTask {

    private final ProxyServer server;

    public VelocityCommandsTask(final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger, final ProxyServer server) {
        super(httpClient, config, logger);
        this.server = server;
    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return this.server.getPlayer(playerName).isPresent();
    }

    @Override
    public void executeCommand(final String command) {
        this.server.getCommandManager().executeAsync(this.server.getConsoleCommandSource(), command);
    }

}
