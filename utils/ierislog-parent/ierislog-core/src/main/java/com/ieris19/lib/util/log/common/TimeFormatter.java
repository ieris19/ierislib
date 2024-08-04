package com.ieris19.lib.util.log.common;

import java.time.format.DateTimeFormatter;

/**
 * Enumeration containing the {@link DateTimeFormatter} to be used in timestamping
 */
public class TimeFormatter {
    /**
     * Formatter for the European standard of Date and Time (dd/MM/yyyy HH:mm:ss)
     */
    public static TimeFormatter EUROPEAN = new TimeFormatter("dd/MM/yyyy HH:mm:ss");
    /**
     * Formatter for the ISO standard of Date and Time (yyyy-MM-dd'T'HH:mm:ss)
     */
    public static TimeFormatter ISO = new TimeFormatter("yyyy-MM-dd'T'HH:mm:ss");
    /**
     * Formatter for the ISO standard of Date only (yyyy-MM-dd)
     */
    public static TimeFormatter DATE_ONLY = new TimeFormatter("yyyy-MM-dd");
    /**
     * Formatter for the ISO standard of Time only (HH:mm:ss)
     */
    public static TimeFormatter TIME_ONLY = new TimeFormatter("HH:mm:ss");

    /**
     * The time formatter for this specific {@link TimeFormatter TimeFormatter}
     */
    private final DateTimeFormatter formatter;

    /**
     * Constructs a formatter from the provided pattern
     *
     * @param pattern the Pattern for the {@link DateTimeFormatter}
     */
    private TimeFormatter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * The formatter corresponding to the selected {@link Enum#name()}
     *
     * @return {@link DateTimeFormatter} of the pattern corresponding to the instance's name
     */
    public DateTimeFormatter get() {
        return this.formatter;
    }

    public static TimeFormatter custom(String pattern) {
        return new TimeFormatter(pattern);
    }
}