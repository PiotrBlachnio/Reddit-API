package com.piotrblachnio.reddit.handlers;

import com.piotrblachnio.reddit.dto.response.ExceptionResponse;
import com.piotrblachnio.reddit.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class, BaseException.class })
    public ResponseEntity<Object> handleException(RuntimeException exception) {
        var response = ExceptionResponse.fromRuntimeException(exception);
        var statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if(exception instanceof BaseException) {
            response = ExceptionResponse.fromBaseException((BaseException) exception);
            statusCode = ((BaseException) exception).getHttpStatus();
        }

        return new ResponseEntity<>(response, statusCode);
    }
}