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

package pl.spaceis.plugin.config;

public abstract class Messages<T> {

    public static final String PREFIX = "&8[SpaceIs]";

    public final T correctSyntax = this.color(PREFIX + " &cPoprawne użycie: &7/spaceis reload");
    public final T noPermission = this.color(PREFIX + " &cBrak uprawnień");
    public final T configReloadError = this.color(PREFIX + " &cBłąd podczas ładowania konfiguracji: &7");
    public final T configReloadSuccess = this.color(PREFIX + " &aKonfiguracja załadowana poprawnie");

    protected abstract T color(final String message);

}
