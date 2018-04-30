package ru.narbut.axel.data.exception;

public class InternetException extends Exception {

    public InternetException() {
        super();
    }

    public InternetException(final String message) {
        super(message);
    }

    public InternetException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InternetException(final Throwable cause) {
        super(cause);
    }
}