package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class InvalidCredentialsException extends BaseException {
        private String message = "Provided credentials are invalid";
        private Integer id = Exception.INVALID_CREDENTIALS;
        private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}