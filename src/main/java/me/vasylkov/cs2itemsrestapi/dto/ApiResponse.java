package me.vasylkov.cs2itemsrestapi.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T>
{
    private boolean success;

    @JsonUnwrapped
    private T data;
}
