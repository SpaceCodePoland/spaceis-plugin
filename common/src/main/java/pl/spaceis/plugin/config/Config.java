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

package pl.spaceis.plugin.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Config {

    public final String serverKey;
    public final String serverId;
    public final String apiKey;
    public final String apiUrl;

    public final Duration taskInterval = Duration.of(60, ChronoUnit.SECONDS);

    public Config(final String serverKey, final String serverId, final String apiKey, final String apiUrl) throws EmptyConfigFieldException {
        this.serverKey = serverKey;
        this.serverId = serverId;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
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
