package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DuplicateUsernameException extends BaseException {
    private String message = "Username already exists";
    private Integer id = Exception.DUPLICATE_USERNAME;
    private HttpStatus httpStatus = HttpStatus.CONFLICT;
}