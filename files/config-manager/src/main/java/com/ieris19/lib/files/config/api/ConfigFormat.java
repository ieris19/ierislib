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
