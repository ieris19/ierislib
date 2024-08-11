package com.ieris19.lib.net.error;

/**
 * This exception is thrown when a recoverable connection error occurs.
 */
public class ConnectionException extends IllegalStateException {
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
