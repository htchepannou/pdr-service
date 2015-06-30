package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.User;

public interface PasswordEncryptor {
    String encrypt (String password);
    boolean matches (String password, User user);
}
