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

package pl.spaceis.plugin.request;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.spaceis.plugin.command.CommandsResponse;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class GetCommandsRequest extends SpaceIsRequest {

    private final Gson gson;

    public GetCommandsRequest(
            final OkHttpClient httpClient,
            final Config config,
            final SpaceIsLogger logger,
            final PlatformDataProvider platformDataProvider
    ) {
        super(httpClient, config, logger, platformDataProvider);
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public CommandsResponse get() throws RequestException {
        final Request request = this.prepareGetRequest(this.getRequestUrl(), this.config.getApiKey(), this.config.getServerKey());

        if (this.config.isDebugEnabled()) {
            this.logger.debug(String.format("Sending GET request to url: %s", request.url()));
            this.logger.debug(String.format("Attaching API key: %s", this.config.getApiKey()));
            this.logger.debug(String.format("Attaching server token: %s", this.config.getServerKey()));
        }

        try (final Response response = this.httpClient.newCall(request).execute()) {
            final String responseBody = response.body() != null ? response.body().string() : null;

            if (this.config.isDebugEnabled()) {
                this.logger.debug(String.format("Response for GET request: %s", responseBody));
            }

            if (!response.isSuccessful()) {
                throw new RequestException("Otrzymany kod odpowiedzi " + response.code());
            }

            if (responseBody == null) {
                throw new RequestException("Puste body odpowiedzi");
            }

            return this.gson.fromJson(responseBody, CommandsResponse.class);
        } catch (final Exception exception) {
            throw new RequestException(exception.getMessage(), exception);
        }
    }

    private String getRequestUrl() throws RequestException {
        final String rawUrl = String.format("%s/server/%s/commands", this.config.getApiUrl(), this.config.getServerId());
        return this.getUrlString(rawUrl);
    }

}
