package com.piotrblachnio.reddit.dto.response;

import com.piotrblachnio.reddit.constants.Exception;
import com.piotrblachnio.reddit.exceptions.BaseException;
import lombok.*;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private final Error error;

    @AllArgsConstructor
    @Data
    private static class Error {
        private final String message;
        private final Integer id;
    }

    public static ExceptionResponse fromRuntimeException(RuntimeException exception) {
        return new ExceptionResponse(new Error(exception.getMessage(), Exception.INTERNAL_SERVER_ERROR));
    }

    public static ExceptionResponse fromBaseException(BaseException exception) {
        return new ExceptionResponse(new Error(exception.getMessage(), exception.getId()));
    }
}