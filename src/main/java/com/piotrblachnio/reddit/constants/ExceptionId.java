package com.piotrblachnio.reddit.constants;

public enum ExceptionId {
    INTERNAL_SERVER_ERROR(0),
    USERNAME_NOT_FOUND(100),
    EMAIL_NOT_FOUND(101);


    private Integer id;
    ExceptionId(Integer id) {}
}
