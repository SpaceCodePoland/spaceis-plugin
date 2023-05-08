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

package pl.spaceis.plugin.request;

import java.net.URI;
import java.net.URL;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class SpaceIsRequest {

    protected String getUrlString(final String rawUrl) throws RequestException {
        try {
            final URL url = new URL(rawUrl);
            final URI uri = new URI(
                    url.getProtocol(),
                    url.getUserInfo(),
                    url.getHost(),
                    url.getPort(),
                    url.getPath(),
                    url.getQuery(),
                    url.getRef()
            );
            return uri.toASCIIString();
        } catch (final Exception exception) {
            throw new RequestException(exception.getMessage(), exception);
        }
    }

    protected Request prepareGetRequest(final String url, final String apiKey, final String serverKey) {
        return this.prepareRequestBuilder(url, apiKey, serverKey).build();
    }

    protected Request preparePostRequest(final String url, final String apiKey, final String serverKey, final RequestBody body) {
        return this.prepareRequestBuilder(url, apiKey, serverKey).post(body).build();
    }

    private Request.Builder prepareRequestBuilder(final String url, final String apiKey, final String serverKey) {
        return new Request.Builder()
                .url(url)
                .header("User-Agent", "SpaceIsPlugin/1.3.1")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("X-SPACEIS-SERVER-TOKEN", serverKey);
    }

}
