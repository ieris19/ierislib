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

package com.ieris19.lib.log.util.config;

import com.ieris19.lib.files.config.FileProperties;
import com.ieris19.lib.log.util.Context;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    /**
     * A package-private constructor to prevent instantiation of this class beyond this package.
     */
    PropertyManager() {
        try {
            this.readProperties();
        } catch (IllegalStateException e) {
            System.err.println("The properties file could not be read. Using default values.");
        }
    }

    /**
     * The names of expected properties in the properties file.
     */
    private String[] logPropertyKeys = {
            "log.level", "log.supportsANSI", "log.file",
            "log.file.directory", "log.file.nameScheme", "log.file.customName"
    };

    /**
     * The complete list of properties the logger requires, that are originally set to default values,
     * but can be overridden by the user in the properties file.
     */
    private final static Properties loggerProperties;

    static {
        // Initialize the properties with default values
        loggerProperties = new Properties();
        loggerProperties.setProperty("log.level", "INFO");
        loggerProperties.setProperty("log.supportsANSI", "true");
        loggerProperties.setProperty("log.file", "true");
        loggerProperties.setProperty("log.file.directory", "logs");
        loggerProperties.setProperty("log.file.nameScheme", "date");
        loggerProperties.setProperty("log.file.customName", "");
    }

    /**
     * This method reads the properties from the properties file and modifies {@link PropertyManager#loggerProperties},
     * such that user defined properties override the default values.
     */
    private void readProperties() {
        try (FileProperties config = FileProperties.getInstance("ierislog.properties")) {
            for (String key : logPropertyKeys) {
                try {
                    loggerProperties.put(key, config.getProperty(key));
                } catch (IllegalArgumentException missingProperty) {
                    config.createProperty(key, loggerProperties.getProperty(key));
                }
            }
        } catch (IOException e) {
            System.err.println("The properties file couldn't be saved.");
        }
    }

    public LogProperties getLogProperties() {
        Context userContext = Context.getByName(loggerProperties.getProperty("log.level"));
        int level = userContext != null ? userContext.getLevel() : Context.INFO.getLevel();
        boolean supportsANSI = Boolean.parseBoolean(loggerProperties.getProperty("log.supportsANSI"));
        boolean logToFile = Boolean.parseBoolean(loggerProperties.getProperty("log.file"));
        String logDirectoryPath = loggerProperties.getProperty("log.file.directory");
        String logFileNameScheme = loggerProperties.getProperty("log.file.nameScheme");
        String logFileName = loggerProperties.getProperty("log.file.customName");
        return new LogProperties(level, supportsANSI, logToFile, logDirectoryPath, logFileNameScheme, logFileName);
    }

    public record LogProperties(int level, boolean supportsANSI, boolean useFile,
                                String directoryPath, String namingScheme, String customName) {}
}
