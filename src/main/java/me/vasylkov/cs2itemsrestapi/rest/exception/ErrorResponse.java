package me.vasylkov.cs2itemsrestapi.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse
{
    private final String reason;
}
