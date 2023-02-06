/*
 * Copyright (C) 2022 SpaceIs-plugin Contributors
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

import java.io.File;
import java.nio.file.Path;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import pl.spaceis.plugin.resource.ResourceLoader;
import pl.spaceis.plugin.resource.ResourceLoaderException;
import pl.spaceis.plugin.resource.ResourceLoaderException.Reason;

public class BungeeResourceLoader extends ResourceLoader<Configuration> {

    public BungeeResourceLoader(final Class<?> loadingClass, final File dataFolder) {
        super(loadingClass, dataFolder.toPath());
    }

    @Override
    public Configuration loadResource(final Path resourcePath) throws ResourceLoaderException {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(resourcePath.toFile());
        } catch (final Exception exception) {
            throw new ResourceLoaderException(Reason.FILE_NOT_LOADED, resourcePath.getFileName().toString(), exception);
        }
    }

}
