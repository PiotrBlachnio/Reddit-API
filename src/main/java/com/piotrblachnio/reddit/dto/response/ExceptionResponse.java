package com.piotrblachnio.reddit.dto.response;

import com.piotrblachnio.reddit.constants.Exception;
import com.piotrblachnio.reddit.exceptions.BaseException;
import lombok.*;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private Integer id;

    public static ExceptionResponse fromRuntimeException(RuntimeException exception) {
        return new ExceptionResponse(exception.getMessage(), Exception.INTERNAL_SERVER_ERROR);
    }

    public static ExceptionResponse fromBaseException(BaseException exception) {
        return new ExceptionResponse(exception.getMessage(), exception.getId());
    }
}