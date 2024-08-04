package com.ieris19.lib.util.console;

/**
 * Thrown in the {@link Console} to indicate that the command is not valid
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * Constructs an {@code InvalidCommandException} with no detail message.
     */
    public InvalidCommandException() {
        super();
    }

    /**
     * Constructs an {@code InvalidCommandException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}