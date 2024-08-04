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
