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

package com.ieris19.lib.files.config.internal;

import com.ieris19.lib.files.config.api.FileConfigManager;
import com.ieris19.lib.files.config.base.BaseConfigManager;
import com.ieris19.lib.files.config.base.BaseFileConfigManager;
import org.ini4j.Options;

import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class PropertiesConfigManager extends BaseFileConfigManager {
    Options config;

    public PropertiesConfigManager(URI path) {
        super(path);
        try {
            this.config = new Options(path.toURL());
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public void loadProperties() {
        try {
            this.config.load();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(this.config.fetch(key));
    }

    @Override
    public void setProperty(String key, String value) {
        this.config.put(key, value);
    }

    @Override
    public void deleteProperty(String key) {
        this.config.remove(key);
    }

    @Override
    public boolean propertyPresent(String key) {
        return this.config.containsKey(key);
    }

    @Override
    public void save() {
        try {
            this.config.store();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
