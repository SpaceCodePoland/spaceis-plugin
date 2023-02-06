package pl.spaceis.plugin.bukkit;

import org.bukkit.ChatColor;
import pl.spaceis.plugin.config.Messages;

public class BukkitMessages extends Messages<String> {

    @Override
    protected String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String appendMessage(final String message, final String append) {
        return message + append;
    }

}
