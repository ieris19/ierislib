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

import com.ieris19.lib.log.util.TemporalHandler;
import com.ieris19.lib.log.util.TextField;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;

/**
 * A class that represents the naming scheme of the log files. It contains the default schemes as methods and it
 * can parse custom schemes from the properties.
 */
class NamingScheme {
    /**
     * Parses the scheme type and the custom value and returns the corresponding TextField
     *
     * @param scheme      The scheme type name (generic, custom, class, date are recognized)
     * @param customValue The custom value to use for the scheme
     * @return The TextField that represents the scheme
     */
    static TextField parseScheme(String scheme, String customValue) {
        return switch (scheme.toLowerCase()) {
            case "custom" -> (args) -> customValue;
            case "generic" -> (args) -> "application.log";
            case "class" -> customClassScheme(customValue);
            case "date" -> dateScheme();
            default -> args -> (customValue.trim().isEmpty() ? scheme : customValue) + ".log";
        };
    }

    private static TextField customClassScheme(String custom) {
        TextField classScheme;
        String className = custom.substring(0, custom.length() - ".class".length());
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isInstance(TextField.class)) {
                classScheme = (TextField) clazz.getDeclaredConstructors()[0].newInstance();
            } else {
                throw new ClassCastException();
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            System.err.println("The class " + className + " could not be used. Using the class name instead");
            e.printStackTrace();
            classScheme = (args) -> custom + ".log";
        }
        return classScheme;
    }

    private static TextField dateScheme() {
        return (args) -> TemporalHandler.getFormatter("dateISO").format(Instant.now()) + ".log";
    }
}
