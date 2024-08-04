package com.ieris19.lib.files.config.error;

/**
 * A type of exception thrown when a property was expected to exist or not, but the opposite was found
 */
public class IncorrectPropertyStatusException extends RuntimeException {
    public IncorrectPropertyStatusException(String message) {
        super(message);
    }

    public IncorrectPropertyStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
