package com.ieris19.lib.reflection.error;

public class NoSuchPackageException extends ReflectiveOperationException {
    public NoSuchPackageException() {
        super();
    }

    public NoSuchPackageException(String message) {
        super(message);
    }

    public NoSuchPackageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPackageException(Throwable cause) {
        super(cause);
    }
}
