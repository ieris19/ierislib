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

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;

import static java.time.temporal.ChronoField.*;

/**
 * A class that handles the temporal context of the logger.
 */
public class TemporalHandler {
    /**
     * A map of formatters for dates and times
     */
    private static final HashMap<String, DateTimeFormatter> formatters;

    static {
        formatters = new HashMap<>();
        formatters.put("fullISO", new TemporalHandler().fullISO());
        formatters.put("dateISO", new TemporalHandler().dateISO());
        formatters.put("timeISO", new TemporalHandler().timeISO());
    }

    /**
     * Produces a formatter for dates and time in a standardized formats.
     * @return a format like "2011-12-03T10:15:30+01:00"
     */
    private DateTimeFormatter fullISO() {
        return new DateTimeFormatterBuilder()
                .append(dateISO())
                .appendLiteral('T')
                .append(timeISO())
                .toFormatter();
    }

    /**
     * Produces a formatter for dates in a standardized formats.
     * @return a format like "2011-12-03"
     */
    private DateTimeFormatter dateISO() {
        return new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .toFormatter();
    }

    /**
     * Produces a formatter for time in a standardized formats.
     * @return a format like "10:15:30+01:00"
     */
    private DateTimeFormatter timeISO() {
        return new DateTimeFormatterBuilder()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .appendOffset("+HH:MM", "")
                .toFormatter();
    }

    /**
     * Adds a new formatter to the map.
     *
     * @param name the name that will be used as the key for the formatter
     * @param formatter the formatter to add to the map
     */
    public static void addFormatter(String name, DateTimeFormatter formatter) {
        formatters.put(name, formatter);
    }

    /**
     * Returns a formatter from the map.
     *
     * @param name the name of the formatter to retrieve
     * @return the formatter with the given name
     */
    public static DateTimeFormatter getFormatter(String name) {
        DateTimeFormatter requested = formatters.get(name);
        return requested == null ? formatters.get("fullISO") : requested;
    }
}
