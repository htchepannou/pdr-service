package com.tchepannou.pdr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "access_denied")
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(){

    }
}
