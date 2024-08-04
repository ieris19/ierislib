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

import com.ieris19.lib.files.config.internal.IniConfigManager;
import com.ieris19.lib.files.config.internal.MemoryConfigManager;
import com.ieris19.lib.files.config.internal.PropertiesConfigManager;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
    private static Map<ConfigFormat, Map<Object, ConfigManager>> configMap;

    static {
        configMap = new HashMap<>();
        for (ConfigFormat format : ConfigFormat.values()) {
            configMap.put(format, new HashMap<>());
        }
    }

    public static ConfigManager getConfig(Object key, ConfigFormat format) {
        if (configMap.get(format).containsKey(key)) {
            return configMap.get(format).get(key);
        }
        if (!key.getClass().isAssignableFrom(format.getKeyType())) {
            throw new IllegalArgumentException("Key type does not match the expected type for the format");
        }
        ConfigManager conf = switch (format) {
            case KEY_VALUE -> new PropertiesConfigManager((URI) key);
            case INI -> new IniConfigManager((URI) key);
            case MEMORY -> new MemoryConfigManager();
        };
        configMap.get(format).put(key, conf);
        return conf;
    }
}
