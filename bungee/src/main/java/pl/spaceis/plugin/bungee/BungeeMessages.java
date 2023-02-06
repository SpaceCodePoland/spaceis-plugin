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

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import pl.spaceis.plugin.config.Messages;

public class BungeeMessages extends Messages<BaseComponent> {

    @Override
    protected BaseComponent color(final String message) {
        return new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
    }

    @Override
    public BaseComponent appendMessage(final BaseComponent message, final String append) {
        BaseComponent finalMessage = message.duplicate();
        finalMessage.addExtra(append);
        return finalMessage;
    }

}
