package pl.spaceis.plugin.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import pl.spaceis.plugin.config.Messages;

public class BungeeMessages extends Messages<BaseComponent> {

    @Override
    protected BaseComponent color(final String message) {
        return new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
    }

}
