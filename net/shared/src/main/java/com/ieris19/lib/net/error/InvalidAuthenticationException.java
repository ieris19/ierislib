package com.ieris19.lib.net.error;

public class InvalidAuthenticationException extends ConnectionException {
    public InvalidAuthenticationException(String message) {
        super(message);
    }

    public InvalidAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
