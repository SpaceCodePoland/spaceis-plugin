/*
 * Copyright (C) 2023 SpaceIs-plugin Contributors
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

package pl.spaceis.plugin.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.spaceis.plugin.command.SpaceIsCommand;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.Messages;

public class BukkitSpaceIsCommand implements CommandExecutor, SpaceIsCommand<CommandSender, String> {

    private final Config config;
    private final Messages<String> messages;

    public BukkitSpaceIsCommand(final Config config, Messages<String> messages) {
        this.config = config;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        this.execute(sender, args);
        return true;
    }

    @Override
    public void sendMessage(final CommandSender sender, final String message) {
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
    public Messages<String> getMessages() {
        return this.messages;
    }

}
