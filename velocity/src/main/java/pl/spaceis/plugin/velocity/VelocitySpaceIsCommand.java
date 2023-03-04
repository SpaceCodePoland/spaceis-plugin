package pl.spaceis.plugin.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import pl.spaceis.plugin.command.SpaceIsCommand;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.Messages;

public class VelocitySpaceIsCommand implements SimpleCommand, SpaceIsCommand<CommandSource, Component> {

    private final Config config;
    private final Messages<Component> messages;

    public VelocitySpaceIsCommand(final Config config, final Messages<Component> messages) {
        this.config = config;
        this.messages = messages;
    }

    @Override
    public void execute(final Invocation invocation) {
        this.executeCommand(invocation.source(), invocation.arguments());
    }

    @Override
    public void sendMessage(final CommandSource sender, final Component message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(final CommandSource sender, final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Messages<Component> getMessages() {
        return this.messages;
    }

}
