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

package pl.spaceis.plugin.velocity.task;

import com.velocitypowered.api.proxy.ProxyServer;
import okhttp3.OkHttpClient;

import pl.spaceis.plugin.command.CommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.velocity.VelocitySpaceIsPlugin;

public class VelocityCommandsTask extends CommandsTask {

    private final VelocitySpaceIsPlugin plugin;

    public VelocityCommandsTask(final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger, VelocitySpaceIsPlugin plugin) {
        super(httpClient, config, logger);
        this.plugin = plugin;

    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return plugin.server.getPlayer(playerName).isPresent();
    }

    @Override
    public void executeCommand(final String command) {
        plugin.server.getCommandManager().executeAsync(this.plugin.server.getConsoleCommandSource(), command);
    }

}
