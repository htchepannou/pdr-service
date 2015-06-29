package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.service.PasswordEncryptor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Md5PasswordEncryptorTest {
    private final PasswordEncryptor service = new Md5PasswordEncryptor();

    @Test
    public void testEncrypt_null() throws Exception {
        assertThat(service.encrypt(null)).isNull();
    }

    @Test
    public void testEncrypt() throws Exception {
        assertThat(service.encrypt("secret")).isEqualTo("5ebe2294ecd0e0f08eab7690d2a6ee69");
    }
}
