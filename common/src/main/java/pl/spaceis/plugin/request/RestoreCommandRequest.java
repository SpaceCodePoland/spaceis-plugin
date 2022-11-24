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

public class RestoreCommandRequest extends SpaceIsRequest {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final RequestBody REQUEST_BODY = RequestBody.create("", JSON_MEDIA_TYPE);

    private final OkHttpClient httpClient;
    private final Config config;

    public RestoreCommandRequest(final OkHttpClient httpClient, final Config config) {
        this.httpClient = httpClient;
        this.config = config;
    }

    public void restore(final UUID commandId) throws RequestException {
        final Request request = this.preparePostRequest(
                this.getRequestUrl(commandId),
                this.config.apiKey,
                this.config.serverKey,
                REQUEST_BODY
        );

        try (final Response response = this.httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RequestException("Otrzymany kod odpowiedzi " + response.code());
            }
        } catch (final Exception exception) {
            throw new RequestException(exception.getMessage(), exception);
        }
    }

    private String getRequestUrl(final UUID commandId) throws RequestException {
        return this.getUrlString(String.format("%s/server/%s/commands/%s/restore", this.config.apiUrl, this.config.serverId, commandId));
    }

}
