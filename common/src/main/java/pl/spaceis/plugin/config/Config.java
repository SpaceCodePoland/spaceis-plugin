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

package pl.spaceis.plugin.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import pl.spaceis.plugin.resource.ResourceLoaderException;

public class Config {

    private final ConfigLoader configLoader;

    private String serverKey;
    private String serverId;
    private String apiKey;
    private String apiUrl;
    private boolean debug;

    public final Duration taskInterval = Duration.of(60, ChronoUnit.SECONDS);

    public Config(final ConfigLoader configLoader) throws ResourceLoaderException, EmptyConfigFieldException {
        this.configLoader = configLoader;
        this.loadValues();
    }

    public String getServerKey() {
        return this.serverKey;
    }

    public String getServerId() {
        return this.serverId;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public boolean isDebugEnabled() {
        return this.debug;
    }

    public void loadValues() throws ResourceLoaderException, EmptyConfigFieldException {
        this.configLoader.reloadConfig();

        this.serverKey = this.configLoader.getString("serverKey");
        this.serverId = this.configLoader.getString("serverId");
        this.apiKey = this.configLoader.getString("apiKey");
        this.apiUrl = this.configLoader.getString("apiUri");
        this.debug = this.configLoader.getBoolean("debug");

        this.checkValues();
    }

    private void checkValues() throws EmptyConfigFieldException {
        this.checkValue(this.serverKey, "serverKey");
        this.checkValue(this.serverId, "serverId");
        this.checkValue(this.apiKey, "apiKey");
        this.checkValue(this.apiUrl, "apiUri");
    }

    private void checkValue(final String value, final String fieldName) throws EmptyConfigFieldException {
        if (value == null || value.isEmpty()) {
            throw new EmptyConfigFieldException(fieldName);
        }
    }

}
