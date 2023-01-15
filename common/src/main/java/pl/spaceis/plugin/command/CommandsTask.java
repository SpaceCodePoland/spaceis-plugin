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

import java.util.Arrays;
import okhttp3.OkHttpClient;
import pl.spaceis.plugin.config.Config;
import pl.spaceis.plugin.logger.SpaceIsLogger;
import pl.spaceis.plugin.request.GetCommandsRequest;
import pl.spaceis.plugin.request.RequestException;
import pl.spaceis.plugin.request.RestoreCommandRequest;

public abstract class CommandsTask implements Runnable {

    private final GetCommandsRequest getCommandsRequest;
    private final RestoreCommandRequest restoreCommandRequest;
    private final SpaceIsLogger logger;

    protected CommandsTask(final OkHttpClient httpClient, final Config config, final SpaceIsLogger logger) {
        this.getCommandsRequest = new GetCommandsRequest(httpClient, config, logger);
        this.restoreCommandRequest = new RestoreCommandRequest(httpClient, config, logger);
        this.logger = logger;
    }

    public abstract boolean isPlayerOnline(final String playerName);

    public abstract void executeCommand(final String command);

    @Override
    public void run() {
        try {
            final CommandsResponse response = this.getCommandsRequest.get();
            if (!response.success()) {
                throw new RequestException("Odpowiedź niezakończona sukcesem");
            }

            Arrays.stream(response.getData()).forEach(this::processCommand);
        } catch (final RequestException exception) {
            this.logger.error("Nieudane pobranie oczekujących komend ze SpaceIs");
            this.logger.error("Przyczyna: " + exception.getMessage());
        }
    }

    private void processCommand(final CommandData commandData) {
        if (commandData.requiresOnline() && !this.isPlayerOnline(commandData.getNick())) {
            try {
                this.restoreCommandRequest.restore(commandData.getId());
            } catch (final RequestException exception) {
                this.logger.error("Nieudane przywrócenie niewykonanej komendy w SpaceIs");
                this.logger.error("Przyczyna: " + exception.getMessage());
            }

            return;
        }

        final String command = commandData.getCommand();
        this.logger.info(String.format("Wykonywanie komendy o id %s: %s", commandData.getId(), command));
        this.executeCommand(command);
    }

}
