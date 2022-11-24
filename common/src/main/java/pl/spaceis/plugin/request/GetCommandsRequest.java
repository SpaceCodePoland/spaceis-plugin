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
import okhttp3.ResponseBody;
import pl.spaceis.plugin.command.CommandsResponse;
import pl.spaceis.plugin.config.Config;

public class GetCommandsRequest extends SpaceIsRequest {

    private final Gson gson;
    private final OkHttpClient httpClient;
    private final Config config;

    public GetCommandsRequest(final OkHttpClient httpClient, final Config config) {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        this.httpClient = httpClient;
        this.config = config;
    }

    public CommandsResponse get() throws RequestException {
        final Request request = this.prepareGetRequest(this.getRequestUrl(), this.config.apiKey, this.config.serverKey);

        try (final Response response = this.httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RequestException("Otrzymany kod odpowiedzi " + response.code());
            }

            final ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new RequestException("Puste body odpowiedzi");
            }

            return this.gson.fromJson(responseBody.string(), CommandsResponse.class);
        } catch (final Exception exception) {
            throw new RequestException(exception.getMessage(), exception);
        }
    }

    private String getRequestUrl() throws RequestException {
        return this.getUrlString(String.format("%s/server/%s/commands", this.config.apiUrl, this.config.serverId));
    }

}
