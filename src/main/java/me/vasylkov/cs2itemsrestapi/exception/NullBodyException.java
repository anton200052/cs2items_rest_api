package me.vasylkov.cs2itemsrestapi.exception;

public class NullBodyException extends RuntimeException
{
    public NullBodyException(String message)
    {
        super(message);
    }
}

