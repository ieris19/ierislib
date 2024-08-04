package com.ieris19.lib.files.config.internal;

import com.ieris19.lib.files.config.base.BaseConfigManager;

import java.util.Optional;
import java.util.Properties;

public class MemoryConfigManager extends BaseConfigManager {
    Properties memoryStorage;

    @Override
    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(memoryStorage.getProperty(key));
    }

    @Override
    public void setProperty(String key, String value) {
        memoryStorage.setProperty(key, value);
    }

    @Override
    public void deleteProperty(String key) {
        memoryStorage.remove(key);
    }

    @Override
    public boolean propertyPresent(String key) {
        return memoryStorage.containsKey(key);
    }
}
