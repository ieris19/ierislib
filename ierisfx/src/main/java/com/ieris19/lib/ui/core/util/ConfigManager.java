/*
 * Copyright 2021 Ieris19
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

package com.ieris19.lib.ui.core.util;

import com.ieris19.lib.files.config.FileProperties;
import com.ieris19.lib.files.config.PropertyTypeException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Manager for all the configuration in the application. This class is responsible for loading and storing
 * the appropriate configuration when requested and also for providing the default configuration.
 */
@Slf4j
public class ConfigManager implements Closeable {
    /**
     * The singleton instance of the ConfigManager
     */
    private static ConfigManager instance;

    /**
     * Returns the singleton instance of the ConfigManager
     * @return the unique ConfigManager
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * The properties file for the application
     */
    private final FileProperties uiProperties;
    /**
     * The name of the application being run
     */
    @Getter
    private final String name;
    /**
     * The default view to be displayed when the application starts
     */
    @Getter
    private final String defaultView;
    /**
     * Whether the application window is resizable or not
     */
    @Getter
    private final boolean resizable;
    /**
     * The icon to be displayed by the application window
     */
    @Getter
    private final URL icon;

    /**
     * Creates a new ConfigManager to handle the configuration in the application
     */
    private ConfigManager() {
        this.uiProperties = FileProperties.getInstance("ierisfx");
        try {
            this.resizable = uiProperties.getPropertyBoolean("resizable");
            this.defaultView = uiProperties.getProperty("default-view");
            this.name = uiProperties.getProperty("name");
            this.icon = new URL(uiProperties.getProperty("icon"));
        } catch (IllegalArgumentException e) {
            log.warn("Insufficient properties in config file", e);
            defaultProperties();
            throw new IllegalStateException("IerisFX is missing minimum configuration");
        } catch (PropertyTypeException | MalformedURLException e) {
            log.warn("Invalid property type in config file", e);
            throw new IllegalStateException("IerisFX is wrongly configured");
        }
    }

    private void defaultProperties() {
        log.debug("Setting default properties");
        uiProperties.createDefaultProperties(new String[][]{
                {"name", "ierisFX"},
                {"default-view", ""},
                {"resizable", "false"},
                {"icon", ""}
        });
        log.warn("Default properties set");
    }

    @Override
    public void close() throws IOException {
        uiProperties.close();
    }
}
