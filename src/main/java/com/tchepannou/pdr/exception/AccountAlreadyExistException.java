package com.tchepannou.pdr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "account_already_exist")
public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException() {
    }
}
