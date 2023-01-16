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

package pl.spaceis.plugin.bukkit;

import okhttp3.OkHttpClient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.spaceis.plugin.bukkit.commands.SpaceIsCommand;
import pl.spaceis.plugin.bukkit.config.BukkitConfigLoader;
import pl.spaceis.plugin.bukkit.logger.BukkitSpaceIsLogger;
import pl.spaceis.plugin.bukkit.task.BukkitCommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class BukkitSpaceIsPlugin extends JavaPlugin {

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Override
    public void onEnable() {
        try {
            final SpaceIsLogger logger = new BukkitSpaceIsLogger(this.getLogger());
            final ConfigLoader configLoader = new BukkitConfigLoader(this);
            final Config config = new Config(configLoader);

            Bukkit.getScheduler().runTaskTimerAsynchronously(
                    this,
                    new BukkitCommandsTask(this, this.httpClient, config, logger),
                    0L,
                    config.taskInterval.getSeconds() * 20L
            );

            this.getCommand("spaceis").setExecutor(new SpaceIsCommand(config));
        } catch (final EmptyConfigFieldException exception) {
            this.getLogger().severe(exception.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        this.httpClient.dispatcher().executorService().shutdown();
    }

}
