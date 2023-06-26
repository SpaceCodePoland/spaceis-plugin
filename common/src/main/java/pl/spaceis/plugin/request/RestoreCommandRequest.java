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

import java.util.UUID;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;

public class RestoreCommandRequest extends SpaceIsRequest {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final RequestBody REQUEST_BODY = RequestBody.create("", JSON_MEDIA_TYPE);

    public RestoreCommandRequest(
            final OkHttpClient httpClient,
            final Config config,
            final SpaceIsLogger logger,
            final PlatformDataProvider platformDataProvider
    ) {
        super(httpClient, config, logger, platformDataProvider);
    }

    public void restore(final UUID commandId) throws RequestException {
        final Request request = this.preparePostRequest(
                this.getRequestUrl(commandId),
                this.config.getApiKey(),
                this.config.getServerKey(),
                REQUEST_BODY
        );

        if (this.config.isDebugEnabled()) {
            this.logger.debug(String.format("Sending POST request to url: %s", request.url()));
            this.logger.debug(String.format("Attaching API key: %s", this.config.getApiKey()));
            this.logger.debug(String.format("Attaching server token: %s", this.config.getServerKey()));
        }

        try (final Response response = this.httpClient.newCall(request).execute()) {
            final String responseBody = response.body() != null ? response.body().string() : null;

            if (this.config.isDebugEnabled()) {
                this.logger.debug(String.format("Response for POST request: %s", responseBody));
            }

            if (!response.isSuccessful()) {
                throw new RequestException("Otrzymany kod odpowiedzi " + response.code());
            }
        } catch (final Exception exception) {
            throw new RequestException(exception.getMessage(), exception);
        }
    }

    private String getRequestUrl(final UUID commandId) throws RequestException {
        final String rawUrl = String.format(
                "%s/server/%s/commands/%s/restore",
                this.config.getApiUrl(),
                this.config.getServerId(),
                commandId
        );
        return this.getUrlString(rawUrl);
    }

}
