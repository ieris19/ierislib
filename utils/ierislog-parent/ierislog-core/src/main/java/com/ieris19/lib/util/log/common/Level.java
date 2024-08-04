package com.ieris19.lib.util.log.common;

/**
 * An enum that contains all the possible severity levels for a log message
 */
public enum Level {
    /**
     * Fatal error that interrupts the functioning of the program
     */
    FATAL(0),
    /**
     * Error that doesn't interrupt the functioning of the program
     */
    ERROR(0),
    /**
     * Warning that something went wrong but doesn't affect the functioning of the program
     */
    WARNING(1),
    /**
     * Success message that indicates that something went right
     */
    SUCCESS(2),
    /**
     * Informational message that indicates something that happened
     */
    INFO(3),
    /**
     * Debug message that indicates actions that are being performed step by step
     */
    DEBUG(4),
    /**
     * Trace message that indicates the flow of the program
     */
    TRACE(5);

    /**
     * The level of the severity
     */
    private final int logLevel;

    /**
     * Creates a new Level with the specified index
     *
     * @param logLevel The index of the severity as an integer
     */
    Level(int logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Returns the severity as an integer for comparison purposes
     *
     * @return The severity level as an integer
     */
    public int value() {
        return logLevel;
    }
}
