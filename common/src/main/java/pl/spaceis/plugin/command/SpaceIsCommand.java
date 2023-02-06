package pl.spaceis.plugin.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public interface SpaceIsCommand<S, M> {

    Set<String> RELOAD_ARGS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("rl", "reload")));

    default void execute(final S sender, final String[] args) {
        if (!this.hasPermission(sender, "spaceis.reload")) {
            this.sendMessage(sender, this.getMessages().noPermission);
            return;
        }

        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            this.sendMessage(sender, this.getMessages().correctSyntax);
            return;
        }

        try {
            this.getConfig().loadValues();
            this.sendMessage(sender, this.getMessages().configReloadSuccess);
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            M message = this.getMessages().appendMessage(this.getMessages().configReloadError, exception.getMessage());
            this.sendMessage(sender, message);
        }
    }

    void sendMessage(final S sender, final M message);

    boolean hasPermission(final S sender, final String permission);

    Config getConfig();

    Messages<M> getMessages();

}
