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

package com.ieris19.lib.files.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {
    private static Map<ConfigFormat, Map<URI, ConfigManager>> configMap;

    static {
        configMap = new HashMap<>();
        for (ConfigFormat format : ConfigFormat.values()) {
            configMap.put(format, new HashMap<>());
        }
    }

    public static ConfigManager getConfig(URI path, ConfigFormat format) {
        if (configMap.get(format).containsKey(path)) {
            return configMap.get(format).get(path);
        }
        ConfigManager conf = switch (format) {
            case KEY_VALUE ->  new PropertiesConfigManager(path);
            case INI -> new IniConfigManager(path);
        };
        configMap.get(format).put(path, conf);
        return conf;
    }

    public enum ConfigFormat {
        KEY_VALUE,
        INI
    }
}
