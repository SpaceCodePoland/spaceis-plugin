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

import net.md_5.bungee.config.Configuration;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class BungeeConfigLoader implements ConfigLoader {

    private final ResourceLoader<Configuration> resourceLoader;
    private final String configFileName;
    private Configuration configFile;

    public BungeeConfigLoader(final ResourceLoader<Configuration> resourceLoader, final String configFileName) {
        this.resourceLoader = resourceLoader;
        this.configFileName = configFileName;
    }

    @Override
    public void reloadConfig() throws ResourceLoaderException {
        this.resourceLoader.saveDefault(this.configFileName);
        this.configFile = this.resourceLoader.load(this.configFileName);
    }

    @Override
    public boolean getBoolean(final String key) {
        return this.configFile.getBoolean(key);
    }

    @Override
    public String getString(final String key) {
        return this.configFile.getString(key);
    }

}
