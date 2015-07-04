package com.tchepannou.pdr.exception;

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
