package com.ieris19.lib.files.config.error;

/**
 * A type of exception thrown when a key is not valid for the current context
 */
public class InvalidKeyException extends RuntimeException {
    public InvalidKeyException(String message) {
        super(message);
    }

    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
