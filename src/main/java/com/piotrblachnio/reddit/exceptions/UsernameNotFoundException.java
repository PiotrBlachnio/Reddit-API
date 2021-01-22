package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UsernameNotFoundException extends BaseException {
    private String message = "Username not found";
    private Integer id = Exception.USERNAME_NOT_FOUND;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
}