package com.ieris19.lib.files.config.base;

import com.ieris19.lib.files.config.api.FileConfigManager;

import java.net.URI;

public abstract class BaseFileConfigManager extends BaseConfigManager implements FileConfigManager {
    URI path;

    public BaseFileConfigManager(URI path) {
        this.path = path;
    }

    @Override
    public URI getPropertiesPath() {
        return path;
    }
}
