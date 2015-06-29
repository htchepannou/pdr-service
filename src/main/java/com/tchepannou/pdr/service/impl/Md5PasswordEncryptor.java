package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.service.PasswordEncryptor;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5PasswordEncryptor implements PasswordEncryptor {
    @Override
    public String encrypt(String password) {
        return password != null ? DigestUtils.md5Hex(password) : null;
    }
}
