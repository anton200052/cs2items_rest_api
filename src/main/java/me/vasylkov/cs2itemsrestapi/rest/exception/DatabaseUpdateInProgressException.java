package me.vasylkov.cs2itemsrestapi.rest.exception;

public class DatabaseUpdateInProgressException extends RuntimeException
{
    public DatabaseUpdateInProgressException(String message)
    {
        super(message);
    }
}
