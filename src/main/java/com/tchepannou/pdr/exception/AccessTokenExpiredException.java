package com.tchepannou.pdr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "token_expired")
public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException(long id){
        super(String.valueOf(id));
    }
}
