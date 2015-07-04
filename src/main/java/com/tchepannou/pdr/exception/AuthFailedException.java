package com.tchepannou.pdr.exception;

public class AuthFailedException extends RuntimeException {
    public AuthFailedException(){

    }
    public AuthFailedException(Throwable cause){
        super(cause);
    }
}
