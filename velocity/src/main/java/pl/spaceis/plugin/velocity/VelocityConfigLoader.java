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

package pl.spaceis.plugin.velocity;

import ninja.leaping.configurate.ConfigurationNode;
import pl.spaceis.plugin.config.ConfigLoader;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class VelocityConfigLoader implements ConfigLoader {

    private final ResourceLoader<ConfigurationNode> resourceLoader;
    private final String configFileName;
    private ConfigurationNode configFile;

    public VelocityConfigLoader(final ResourceLoader<ConfigurationNode> resourceLoader, final String configFileName) {
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
        return this.configFile.getNode(key).getBoolean();
    }

    @Override
    public String getString(final String key) {
        return this.configFile.getNode(key).getString();
    }

}
