package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExpiredTokenException extends BaseException {
    private String message = "Provided token is expired";
    private Integer id = Exception.EXPIRED_TOKEN;
    private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}
