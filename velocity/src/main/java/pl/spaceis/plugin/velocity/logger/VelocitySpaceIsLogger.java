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

package pl.spaceis.plugin.velocity.logger;

import org.slf4j.Logger;
import pl.spaceis.plugin.logger.SpaceIsLogger;



public class VelocitySpaceIsLogger implements SpaceIsLogger {

    private final Logger logger;

    public VelocitySpaceIsLogger(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(final String message) {
        this.logger.info(message);
    }

    @Override
    public void error(final String message) {
        this.logger.error(message);
    }

    @Override
    public void debug(final String message) {
        this.logger.info(String.format("[DEBUG] %s", message));
    }

}
