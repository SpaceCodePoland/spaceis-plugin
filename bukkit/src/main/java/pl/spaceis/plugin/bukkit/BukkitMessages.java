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

package pl.spaceis.plugin.bukkit;

import org.bukkit.ChatColor;
import pl.spaceis.plugin.config.Messages;

public class BukkitMessages extends Messages<String> {

    @Override
    protected String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public String appendMessage(final String message, final String append) {
        return message + append;
    }

}
