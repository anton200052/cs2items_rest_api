package me.vasylkov.cs2itemsrestapi.rest.exception;

import me.vasylkov.cs2itemsrestapi.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEmployeeNotFoundException(EntityNotFoundException exception)
    {
        return new ResponseEntity<>(new ErrorDTO(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleTypeMismatchException(MissingServletRequestParameterException exception)
    {
        return new ResponseEntity<>(new ErrorDTO(String.format("Missing %s parameter", exception.getParameterName())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseUpdateInProgressException.class)
    public ResponseEntity<?> handleTypeMismatchException(DatabaseUpdateInProgressException exception)
    {
        return new ResponseEntity<>(new ErrorDTO(exception.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException exception)
    {
        return new ResponseEntity<>(new ErrorDTO("The requested URL was not found on this server."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException e)
    {
        return new ResponseEntity<>(new ErrorDTO("Invalid argument: " + e.getValue()), HttpStatus.BAD_REQUEST);
    }

}
