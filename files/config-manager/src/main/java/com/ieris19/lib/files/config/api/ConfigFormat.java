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

public enum ConfigFormat {
    KEY_VALUE(FileConfigManager.class, URI.class),
    INI(FileConfigManager.class, URI.class),
    MEMORY(ConfigManager.class, Object.class);

    private final Class<? extends ConfigManager> specializedInterface;
    private final Class<?> keyType;

    ConfigFormat(Class<? extends ConfigManager> specializedInterface, Class<?> keyType) {
        this.specializedInterface = specializedInterface;
        this.keyType = keyType;
    }

    public Class<? extends ConfigManager> getSpecializedInterface() {
        return specializedInterface;
    }

    public Class<?> getKeyType() {
        return keyType;
    }
}
