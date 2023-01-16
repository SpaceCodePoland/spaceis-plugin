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

package pl.spaceis.plugin.bukkit.config;

import org.bukkit.plugin.Plugin;
import pl.spaceis.plugin.config.ConfigLoader;

public class BukkitConfigLoader implements ConfigLoader {

    private final Plugin plugin;

    public BukkitConfigLoader(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reloadConfig() {
        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.plugin.getConfig().getBoolean(key);
    }

    @Override
    public String getString(final String key) {
        return this.plugin.getConfig().getString(key);
    }

}
