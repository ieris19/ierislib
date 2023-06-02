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

package com.ieris19.lib.log.core;

import com.ieris19.lib.log.util.config.LogConfigurer;
import com.ieris19.lib.log.core.impl.BaseLogger;
import com.ieris19.lib.log.util.LogPrinter;

import java.util.HashMap;

/**
 * This class is responsible for creating loggers for use in any application. It cannot be instatiated, so only one.
 * It delegates the configuration of the loggers to the LogConfigurer class.
 * It also stores the loggers it creates in a map, so that it can return the same logger if it is requested again.
 *
 * @see LogConfigurer
 * @see IerisLogger
 */
public class LoggerFactory {
    /**
     * The map of all the loggers created by the LoggerFactory.
     */
    private static HashMap<String, IerisLogger> loggers = new HashMap<>();

    /**
     * The maximum level of the messages that will be output by the loggers.
     */
    private static int maxOutputLevel;

    /**
     * The printer that will handle the logging itself
     */
    private static LogPrinter logPrinter;

    static {
        configureLogger();
    }

    private static void configureLogger() {
        LogConfigurer configurer = new LogConfigurer();
        maxOutputLevel = configurer.getLogLevel();
        logPrinter = configurer.getLogPrinter();
    }

    public static IerisLogger getLogger(String name) {
        if (loggers.containsKey(name)) {
            return loggers.get(name);
        }
        else {
            IerisLogger logger = new BaseLogger(maxOutputLevel, logPrinter);
            loggers.put(name, logger);
            return logger;
        }
    }
}
