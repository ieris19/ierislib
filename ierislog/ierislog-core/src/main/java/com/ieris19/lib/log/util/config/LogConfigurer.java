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

import com.ieris19.lib.log.util.TextField;
import com.ieris19.lib.log.util.LogPrinter;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

/**
 * The class responsible for managing the configuration of the logger.
 * This class is called when initializing the LoggerFactory, and it reads the configuration from the properties file.
 */
public class LogConfigurer {
    private TextField namingScheme;
    private LogPrinter logPrinter;
    private int logLevel;

    public LogConfigurer() {
        PropertyManager.LogProperties properties = new PropertyManager().getLogProperties();
        this.namingScheme = NamingScheme.parseScheme(properties.namingScheme(), properties.customName());
        this.logLevel = properties.level();
        logPrinter = initPrinter(properties.supportsANSI(), properties.useFile(),
                                 properties.directoryPath(), namingScheme);
    }

    private LogPrinter initPrinter(boolean supportsANSI, boolean useFile, String outputPath, TextField namingScheme) {
        File logDirectory = null;
        try {
            logDirectory = getLogDirectory(outputPath);
        } catch (FileAlreadyExistsException e) {
            useFile = false;
        }
        return new LogPrinter(supportsANSI, useFile, logDirectory, namingScheme);
    }

    private File getLogDirectory(String directoryPath) throws FileAlreadyExistsException {
        File logDirectory = new File(directoryPath);
        if (!logDirectory.mkdirs()) {
            if (logDirectory.exists() || !logDirectory.isDirectory()) {
                throw new FileAlreadyExistsException("Failed to create log directory");
            }
        }
        return logDirectory;
    }

    public LogPrinter getLogPrinter() {
        return this.logPrinter;
    }

    public int getLogLevel() {
       return this.logLevel;
    }
}
