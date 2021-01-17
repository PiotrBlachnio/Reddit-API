package com.piotrblachnio.reddit.exceptions;

import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {
    private final String message = "Exception occurred!";
    private final Integer id = 0;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
}