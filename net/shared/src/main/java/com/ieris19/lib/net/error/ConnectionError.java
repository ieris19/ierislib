package com.ieris19.lib.net.error;

/**
 * This exception is thrown when an unrecoverable connection error occurs.
 */
public class ConnectionError extends Exception {
    public ConnectionError(String message) {
        super(message);
    }

    public ConnectionError(String message, Throwable cause) {
        super(message, cause);
    }
}
