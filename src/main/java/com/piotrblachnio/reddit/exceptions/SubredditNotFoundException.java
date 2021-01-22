package com.piotrblachnio.reddit.exceptions;

import com.piotrblachnio.reddit.constants.Exception;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SubredditNotFoundException extends BaseException {
    private String message = "Subreddit not found";
    private Integer id = Exception.SUBREDDIT_NOT_FOUND;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
}