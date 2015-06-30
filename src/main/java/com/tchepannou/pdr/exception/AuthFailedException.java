package com.tchepannou.pdr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "auth_failed")
public class AuthFailedException extends RuntimeException {
    public AuthFailedException(){

    }
    public AuthFailedException(Throwable cause){
        super(cause);
    }
}
