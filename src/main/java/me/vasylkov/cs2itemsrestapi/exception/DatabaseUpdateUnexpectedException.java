package me.vasylkov.cs2itemsrestapi.exception;

public class DatabaseUpdateUnexpectedException extends RuntimeException {
    public DatabaseUpdateUnexpectedException(String message) {
        super(message);
    }
}
