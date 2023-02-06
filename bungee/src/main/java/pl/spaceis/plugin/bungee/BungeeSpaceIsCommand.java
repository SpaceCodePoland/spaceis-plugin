/*
 * Copyright (C) 2022 SpaceIs-plugin Contributors
 * https://github.com/SpaceCodePoland/spaceis-plugin/graphs/contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.spaceis.plugin.bungee;

import java.util.Locale;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import pl.spaceis.plugin.command.SpaceIsCommand;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeSpaceIsCommand extends Command implements SpaceIsCommand<CommandSender, BaseComponent> {

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

    @Override
    public void sendMessage(final CommandSender sender, final BaseComponent message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean hasPermission(final CommandSender sender, final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Messages<BaseComponent> getMessages() {
        return this.messages;
    }

}
