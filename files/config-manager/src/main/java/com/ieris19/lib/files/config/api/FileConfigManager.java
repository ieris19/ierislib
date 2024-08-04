/*
 * Copyright 2024 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.files.config.api;

import java.net.URI;

public interface FileConfigManager extends ConfigManager {
    /**
     * Loads the properties from file, the file should be specified in the
     * constructor of the implementing class, as this interface does not have
     * a method to set the file path
     */
    void loadProperties();

    /**
     * Returns the path to the properties file
     *
     * @return the path to the properties file
     */
    URI getPropertiesPath();

    /**
     * Saves the properties to file
     */
    void save();
}
