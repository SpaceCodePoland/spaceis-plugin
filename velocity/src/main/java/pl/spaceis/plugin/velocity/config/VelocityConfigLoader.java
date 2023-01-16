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

package pl.spaceis.plugin.velocity.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.Types;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import pl.spaceis.plugin.config.ConfigLoader;

import java.io.File;
import java.io.IOException;

public class VelocityConfigLoader implements ConfigLoader {

    private ConfigurationNode configFile;
    private final File path;

    public VelocityConfigLoader(File path) {
        this.path = path;
    }

    @Override
    public void reloadConfig() {
        try {
            configFile = YAMLConfigurationLoader.builder().setPath(new File(path, "config.yml").toPath()).build().load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
