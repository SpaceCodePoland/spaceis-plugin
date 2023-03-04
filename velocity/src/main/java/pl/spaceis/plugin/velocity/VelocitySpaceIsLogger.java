package pl.spaceis.plugin.velocity;

import org.slf4j.Logger;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class VelocitySpaceIsLogger implements SpaceIsLogger {

    private final Logger logger;

    public VelocitySpaceIsLogger(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void debug(String message) {
        this.logger.debug(String.format("[DEBUG] %s", message));
    }

}
