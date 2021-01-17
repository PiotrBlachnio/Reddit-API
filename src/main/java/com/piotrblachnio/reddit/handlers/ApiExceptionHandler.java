package com.piotrblachnio.reddit.handlers;

import com.piotrblachnio.reddit.dto.response.ExceptionResponse;
import com.piotrblachnio.reddit.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class, BaseException.class})
    public ResponseEntity<Object> handleException(RuntimeException exception) {
        var response = ExceptionResponse.fromRuntimeException(exception);

        if(exception instanceof BaseException) {
            response = ExceptionResponse.fromBaseException((BaseException) exception);
        }

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}