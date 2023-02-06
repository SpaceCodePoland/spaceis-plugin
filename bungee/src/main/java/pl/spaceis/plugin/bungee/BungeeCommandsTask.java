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

package pl.spaceis.plugin.bungee;

import net.md_5.bungee.api.ProxyServer;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.command.CommandsTask;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class BungeeCommandsTask extends CommandsTask {

    private final ProxyServer proxy;

    public BungeeCommandsTask(final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger) {
        super(httpClient, config, logger);
        this.proxy = ProxyServer.getInstance();
    }

    @Override
    public boolean isPlayerOnline(final String playerName) {
        return this.proxy.getPlayer(playerName) != null;
    }

    @Override
    public void executeCommand(final String command) {
        this.proxy.getPluginManager().dispatchCommand(this.proxy.getConsole(), command);
    }

}
