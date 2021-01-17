package com.piotrblachnio.reddit.dto.response;

import com.piotrblachnio.reddit.constants.ExceptionId;
import com.piotrblachnio.reddit.exceptions.BaseException;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private HttpStatus httpStatus;
    private Integer id;

    public static ExceptionResponse fromRuntimeException(RuntimeException exception) {
        return new ExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ExceptionId.INTERNAL_SERVER_ERROR);
    }

    public static ExceptionResponse fromBaseException(BaseException exception) {
        return new ExceptionResponse(exception.getMessage(),exception.getHttpStatus(), exception.getId());
    }
}