package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DuplicateVoteException extends BaseException {
    private String message = "You've already voted for this post";
    private Integer id = Exception.DUPLICATE_VOTE;
    private HttpStatus httpStatus = HttpStatus.FORBIDDEN;
}
