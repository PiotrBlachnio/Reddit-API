package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DuplicateEmailException extends BaseException {
    private String message = "Email already exists";
    private Integer id = Exception.DUPLICATE_EMAIL;
    private HttpStatus httpStatus = HttpStatus.CONFLICT;
}