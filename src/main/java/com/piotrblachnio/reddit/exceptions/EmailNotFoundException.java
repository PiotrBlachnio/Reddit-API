package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EmailNotFoundException extends BaseException {
    private String message = "Email not found";
    private Integer id = Exception.EMAIL_NOT_FOUND;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
}
