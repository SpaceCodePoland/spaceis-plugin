package pl.spaceis.plugin.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.messages.Messages;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SpaceIsCommand implements SimpleCommand {
    private static final List<String> RELOAD_ARGS = new ArrayList<>(Arrays.asList("rl", "reload"));
    private final Config config;
    private final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder()
            .hexCharacter('#')
            .character('&')
            .useUnusualXRepeatedCharacterHexFormat()
            .extractUrls()
            .build();

    public SpaceIsCommand(Config config) {
        this.config = config;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();


        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            source.sendMessage(serializer.deserialize(Messages.CORRECT_SYNTAX));
            return;
        }
        try {
            this.config.loadValues();
            source.sendMessage(serializer.deserialize(Messages.CONFIG_RELOAD_SUCCESS));
        } catch (final EmptyConfigFieldException exception) {
            source.sendMessage(serializer.deserialize(Messages.CONFIG_RELOAD_ERROR + exception.getMessage()));
        }

    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return RELOAD_ARGS;
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(RELOAD_ARGS);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("spaceis.reload");
    }
}
