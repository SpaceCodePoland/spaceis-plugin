/*
 * Copyright (C) 2022 Kamil Trysiński
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

package pl.spaceis.plugin.bukkit.task;

import okhttp3.OkHttpClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import pl.spaceis.plugin.command.CommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class BukkitCommandsTask extends CommandsTask {

    private final Plugin plugin;

    public BukkitCommandsTask(final Plugin plugin, final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger) {
        super(httpClient, config, logger);
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return Bukkit.getPlayerExact(playerName) != null;
    }

    @Override
    public void executeCommand(final String command) {
        Bukkit.getScheduler().runTask(this.plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

}
