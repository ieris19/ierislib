package com.ieris19.lib.files.config.error;

/**
 * A type of exception thrown when a property was expected to be of a certain type, but it was not
 * possible to convert it into such
 */
public class IncorrectPropertyTypeException extends RuntimeException {
    public IncorrectPropertyTypeException(String message) {
        super(message);
    }

    public IncorrectPropertyTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
