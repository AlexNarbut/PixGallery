package ru.narbut.axel.data.exception;

public class PhotoNotFoundException extends Exception {

    public PhotoNotFoundException() {
        super();
    }

    public PhotoNotFoundException(final String message) {
        super(message);
    }

    public PhotoNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PhotoNotFoundException(final Throwable cause) {
        super(cause);
    }
}