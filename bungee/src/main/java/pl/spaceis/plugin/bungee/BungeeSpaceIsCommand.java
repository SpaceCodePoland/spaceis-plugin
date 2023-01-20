package pl.spaceis.plugin.bungee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeSpaceIsCommand extends Command {

    private static final Set<String> RELOAD_ARGS = new HashSet<>(Arrays.asList("rl", "reload"));
    private final Config config;
    private final Messages<BaseComponent> messages;

    public BungeeSpaceIsCommand(final Config config, final Messages<BaseComponent> messages) {
        super("spaceis");
        this.config = config;
        this.messages = messages;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (!sender.hasPermission("spaceis.reload")) {
            sender.sendMessage(this.messages.noPermission);
            return;
        }

        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            sender.sendMessage(this.messages.correctSyntax);
            return;
        }

        try {
            this.config.loadValues();
            sender.sendMessage(this.messages.configReloadSuccess);
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            BaseComponent errorMessage = this.messages.configReloadError.duplicate();
            errorMessage.addExtra(exception.getMessage());
            sender.sendMessage(errorMessage);
        }
    }

}
