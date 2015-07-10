package com.tchepannou.pds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate_email")
public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String name) {
        super (name);
    }
}
