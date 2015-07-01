package com.tchepannou.pdr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException () {
    }
    public NotFoundException (long id) {
        super (String.valueOf(id));
    }
    public NotFoundException (String msg) {
        super (msg);
    }
}
