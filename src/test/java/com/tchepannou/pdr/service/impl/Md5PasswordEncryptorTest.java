package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.service.PasswordEncryptor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Md5PasswordEncryptorTest {
    private final PasswordEncryptor service = new PasswordEncryptorImpl();

    @Test
    public void test_encrypt_null() throws Exception {
        assertThat(service.encrypt(null)).isNull();
    }

    @Test
    public void test_matches() throws Exception {
        User user = new User ();
        user.setPassword(service.encrypt("secret"));

        assertThat(service.matches("secret", user)).isTrue();
    }

    @Test
    public void test_matches_null() throws Exception {
        User user = new User ();
        user.setPassword(service.encrypt("secret"));

        assertThat(service.matches(null, user)).isFalse();
    }


    @Test
    public void test_matches_all_null() throws Exception {
        assertThat(service.matches(null, new User())).isTrue();
    }
}
