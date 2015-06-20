package com.tchepannou.pdr.domain;

import java.time.LocalDate;

public class User extends Persistent {
    //-- Attributes
    public String login;
    public String password;
    public LocalDate expiryDate;

    //-- Getter/Setter
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
