package pl.spaceis.plugin.bungee;

import java.util.logging.Logger;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class BungeeSpaceIsLogger implements SpaceIsLogger {

    private final Logger logger;

    public BungeeSpaceIsLogger(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(final String message) {
        this.logger.info(message);
    }

    @Override
    public void error(final String message) {
        this.logger.severe(message);
    }

    @Override
    public void debug(final String message) {
        this.logger.info(String.format("[DEBUG] %s", message));
    }

}
