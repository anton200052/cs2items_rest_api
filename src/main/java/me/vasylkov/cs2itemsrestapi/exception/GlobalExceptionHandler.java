package me.vasylkov.cs2itemsrestapi.exception;

import lombok.RequiredArgsConstructor;
import me.vasylkov.cs2itemsrestapi.component.ResponseEntityConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler
{
    private final ResponseEntityConverter<ErrorResponse> converter;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EntityNotFoundException exception)
    {
        return converter.convertToResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleTypeMismatchException(MissingServletRequestParameterException exception)
    {
        return converter.convertToResponseEntity(new ErrorResponse(String.format("Missing %s parameter", exception.getParameterName())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseUpdateInProgressException.class)
    public ResponseEntity<?> handleUpdateInProgressException(DatabaseUpdateInProgressException exception)
    {
        return converter.convertToResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler(DatabaseUpdateUnexpectedException.class)
    public ResponseEntity<?> handleUpdateUnexpectedException(DatabaseUpdateUnexpectedException exception)
    {
        return converter.convertToResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException exception)
    {
        return converter.convertToResponseEntity(new ErrorResponse("The requested URL was not found on this server."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException e)
    {
        return converter.convertToResponseEntity(new ErrorResponse("Invalid argument: " + e.getValue()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullBodyException.class)
    public ResponseEntity<?> handleNullBodyException(NullBodyException e)
    {
        return converter.convertToResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
