package com.ieris19.lib.util.log.common;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * A class that can return the current date and time appropriately formatted for the log console
 */
public class TimestampHandler implements AutoCloseable {
    /**
     * Singleton instance of the timestamp handler
     */
    private static final HashMap<TimeFormatter, TimestampHandler> instances;
    private TimeFormatter defaultFormatter;

    static {
        instances = new HashMap<>();
    }

    /**
     * Private constructor to avoid accidentally creating multiple instances of {@link TimestampHandler}. Please refer to
     * {@link #getInstance(TimeFormatter)} for more information
     */
    private TimestampHandler(TimeFormatter defaultFormatter) {
        this.defaultFormatter = defaultFormatter;
    }

    /**
     * Method to obtain the {@link TimestampHandler} instance, it will only create a new instance on its first call for
     * each {@link TimeFormatter} and return the already created instance every call after that. This means there will
     * always be one single instance of the <code>TimestampHandler</code> class for each {@link TimeFormatter}
     *
     * @return The instance of <code>TimestampHandler</code> for the given {@link TimeFormatter}, regardless of if it's
     * new, or it had been previously created
     */
    public synchronized static TimestampHandler getInstance(TimeFormatter formatter) {
        TimestampHandler instance = instances.get(formatter);
        if (instance == null) {
            instance = new TimestampHandler(formatter);
            instances.put(formatter, instance);
        }
        return instance;
    }

    /**
     * Returns a formatted date and/or time string according to the pattern in the parameter object
     *
     * @param formatter Pattern for the String's format
     * @return A formatted string
     */
    public String getFormatted() {
        return getFormatted(defaultFormatter);
    }

    /**
     * Returns a formatted date and/or time string according to the pattern in the parameter object
     *
     * @param formatter {@link DateTimeFormatter} for the String's format
     * @return A formatted string
     */
    public String getFormatted(TimeFormatter formatter) {
        return getTime().format(formatter.get());
    }

    /**
     * Gets the current UTC time using the system clock
     *
     * @return UTC date and time at the moment of the call
     */
    public LocalDateTime getTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    @Override
    public void close() {
        instances.remove(defaultFormatter);
        this.defaultFormatter = null;
    }
}
