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

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.request.PlatformDataProvider;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeSpaceIsPlugin extends Plugin implements PlatformDataProvider {

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Override
    public void onEnable() {
        final SpaceIsLogger logger = new BungeeSpaceIsLogger(this.getLogger());
        try {
            final ResourceLoader<Configuration> resourceLoader = new BungeeResourceLoader(this.getClass(), this.getDataFolder());
            final ConfigLoader configLoader = new BungeeConfigLoader(resourceLoader, "config.yml");
            final Config config = new Config(configLoader);
            final Messages<BaseComponent> messages = new BungeeMessages();

            this.getProxy().getScheduler().schedule(
                    this,
                    new BungeeCommandsTask(this.getProxy(), this.httpClient, config, logger, this),
                    0L,
                    config.taskInterval.getSeconds(),
                    TimeUnit.SECONDS
            );

            ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeSpaceIsCommand(config, messages));
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            logger.error(exception.getMessage());
            ProxyServer.getInstance().getScheduler().cancel(this);
        }
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getScheduler().cancel(this);
        this.httpClient.dispatcher().executorService().shutdown();
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public String getEngineName() {
        return "BungeeCord";
    }

    @Override
    public String getEngineVersion() {
        return ProxyServer.getInstance().getVersion();
    }
}
