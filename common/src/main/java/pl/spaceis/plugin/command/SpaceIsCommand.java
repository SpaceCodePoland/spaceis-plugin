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

package pl.spaceis.plugin.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.config.EmptyConfigFieldException;
import pl.spaceis.plugin.config.Messages;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public interface SpaceIsCommand<S, M> {

    Set<String> RELOAD_ARGS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("rl", "reload")));

    default void execute(final S sender, final String[] args) {
        if (!this.hasPermission(sender, "spaceis.reload")) {
            this.sendMessage(sender, this.getMessages().noPermission);
            return;
        }

        if (args.length != 1 || !RELOAD_ARGS.contains(args[0].toLowerCase(Locale.ROOT))) {
            this.sendMessage(sender, this.getMessages().correctSyntax);
            return;
        }

        try {
            this.getConfig().loadValues();
            this.sendMessage(sender, this.getMessages().configReloadSuccess);
        } catch (final ResourceLoaderException | EmptyConfigFieldException exception) {
            M message = this.getMessages().appendMessage(this.getMessages().configReloadError, exception.getMessage());
            this.sendMessage(sender, message);
        }
    }

    void sendMessage(final S sender, final M message);

    boolean hasPermission(final S sender, final String permission);

    Config getConfig();

    Messages<M> getMessages();

}
