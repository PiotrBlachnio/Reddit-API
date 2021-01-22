package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidConfirmationTokenException extends BaseException {
    private String message = "Provided confirmation token is invalid";
    private Integer id = Exception.INVALID_CONFIRMATION_TOKEN;
    private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}