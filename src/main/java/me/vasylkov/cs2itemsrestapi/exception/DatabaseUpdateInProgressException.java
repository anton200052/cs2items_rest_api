package me.vasylkov.cs2itemsrestapi.exception;

public class DatabaseUpdateInProgressException extends RuntimeException {
    public DatabaseUpdateInProgressException(String message) {
        super(message);
    }
}
