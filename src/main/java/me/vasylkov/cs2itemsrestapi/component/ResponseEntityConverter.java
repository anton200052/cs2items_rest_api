package me.vasylkov.cs2itemsrestapi.component;

import me.vasylkov.cs2itemsrestapi.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityConverter<T>
{
    public ResponseEntity<ApiResponse<T>> convertToResponseEntity(T entity, HttpStatus status)
    {
        ApiResponse<T> apiResponse = new ApiResponse<>(status.is2xxSuccessful(), entity);
        return new ResponseEntity<>(apiResponse, status);
    }
}
