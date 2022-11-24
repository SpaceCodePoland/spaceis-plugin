/*
 * Copyright (C) 2022 Kamil Trysiński
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

import java.util.Base64;
import java.util.UUID;

public class CommandData {

    private final UUID id;
    private final String command;
    private final int requiresPlayerOnline;
    private final String nick;

    public CommandData(final UUID id, final String command, final int requiresPlayerOnline, final String nick) {
        this.id = id;
        this.command = command;
        this.requiresPlayerOnline = requiresPlayerOnline;
        this.nick = nick;
    }

    public UUID getId() {
        return this.id;
    }

    public String getCommand() {
        return new String(Base64.getDecoder().decode(this.command));
    }

    public boolean requiresOnline() {
        return this.requiresPlayerOnline == 1;
    }

    public String getNick() {
        return this.nick;
    }

}
