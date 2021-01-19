package com.piotrblachnio.reddit.handlers;

import com.piotrblachnio.reddit.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
        var response = ExceptionResponse.fromValidationException(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}