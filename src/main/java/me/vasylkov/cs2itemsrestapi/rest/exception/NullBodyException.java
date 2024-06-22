package me.vasylkov.cs2itemsrestapi.rest.exception;

public class NullBodyException extends RuntimeException
{
    public NullBodyException(String message)
    {
        super(message);
    }
}

