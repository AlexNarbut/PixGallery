package ru.narbut.axel.domain.exception;

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
