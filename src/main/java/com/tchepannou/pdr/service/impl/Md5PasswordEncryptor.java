package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.service.PasswordEncryptor;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5PasswordEncryptor implements PasswordEncryptor {
    @Override
    public String encrypt(String password) {
        return password != null ? DigestUtils.md5Hex(password) : null;
    }

    @Override
    public boolean matches(String password, User user) {
        if (password == null){
            return user.getPassword() == null;
        } else {
            return this.encrypt(password).equals(user.getPassword());
        }
    }
}
