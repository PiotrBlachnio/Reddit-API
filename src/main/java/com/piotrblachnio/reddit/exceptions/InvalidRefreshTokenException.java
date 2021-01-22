package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidRefreshTokenException extends BaseException {
    private String message = "Provided refresh token is invalid";
    private Integer id = Exception.INVALID_REFRESH_TOKEN;
    private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}
