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

package com.ieris19.lib.log.util;

import com.ieris19.lib.common.text.TextColor;

import static com.ieris19.lib.common.text.TextColor.*;

/**
 * An enum of the different contexts to a log message. <br>
 * Each context provides a name and color to be handled by the logger,
 * and a level to be used to filter messages.
 */
public enum Context {
    FATAL(MAGENTA, 0),
    ERROR(RED, 0),
    WARN(YELLOW, 1),
    INFO(BLUE, 2),
    SUCCESS(GREEN, 2),
    DEBUG(CYAN, 3),
    TRACE(BLUE, 4);

    /**
     * The color to enclose the messages in this context in if ANSI escape sequences are enabled
     */
    private TextColor color;
    /**
     * The level of severity for the context
     */
    private int level;

    /**
     * Private constructor for the enum since instantiation outside the enum is illegal
     */
    Context(TextColor color, int level) {
        this.color = color;
        this.level = level;
    }

    /**
     * Returns a context based on its name. If no context with the given name exists, null is returned.
     * Unlike the {@link #valueOf(String)} method, this method is case-insensitive,
     * and it will trim the input before comparing it, making it more forgiving, specially for user input.
     *
     * @param name The name of the context to return
     * @return The context with the given name, or null if no such context exists
     */
    public static Context getByName(String name) {
        for (Context context : Context.values()) {
            if (context.name().equalsIgnoreCase(name.trim())) {
                return context;
            }
        }
        return null;
    }

    public TextColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }
}
