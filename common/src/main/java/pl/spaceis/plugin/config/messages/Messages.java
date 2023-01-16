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

package pl.spaceis.plugin.config.messages;


public final class Messages {

    public static final String PREFIX = "&8[SpaceIs]";
    public static final String CORRECT_SYNTAX = PREFIX +  " &cPoprawne użycie:" + " &7/spaceis reload";
    public static final String NO_PERMISSION = PREFIX + " &cBrak uprawnień";
    public static final String CONFIG_RELOAD_ERROR = PREFIX + " &cBłąd podczas ładowania konfiguracji: &7";
    public static final String CONFIG_RELOAD_SUCCESS = PREFIX + " &aKonfiguracja załadowana poprawnie";

    private Messages() {}

}
