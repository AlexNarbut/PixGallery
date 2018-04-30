package ru.narbut.axel.data.exception;

public class FileWriteException extends Exception {

    public FileWriteException() {
        super();
    }

    public FileWriteException(final String message) {
        super(message);
    }

    public FileWriteException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileWriteException(final Throwable cause) {
        super(cause);
    }
}