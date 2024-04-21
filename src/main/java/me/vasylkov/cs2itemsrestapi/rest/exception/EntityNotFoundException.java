package me.vasylkov.cs2itemsrestapi.rest.exception;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String message)
    {
        super(message);
    }
}
