package com.tchepannou.pdr.exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException(long id){
        super(String.valueOf(id));
    }
}
