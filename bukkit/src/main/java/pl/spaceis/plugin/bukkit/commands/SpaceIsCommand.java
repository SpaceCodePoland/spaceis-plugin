/*
 * Copyright (C) 2023 Kamil Trysiński
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

package pl.spaceis.plugin.bukkit.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.spaceis.plugin.config.messages.Messages;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;

public class SpaceIsCommand implements CommandExecutor {

    private static final Set<String> RELOAD_ARGS = new HashSet<>(Arrays.asList("rl", "reload"));
    private final Config config;

    public SpaceIsCommand(final Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("spaceis.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_PERMISSION));
            return true;
        }

        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Messages.CORRECT_SYNTAX));
            return true;
        }

        try {
            this.config.loadValues();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Messages.CONFIG_RELOAD_SUCCESS));
        } catch (final EmptyConfigFieldException exception) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Messages.CONFIG_RELOAD_ERROR + exception.getMessage()));
        }

        return true;
    }

}
