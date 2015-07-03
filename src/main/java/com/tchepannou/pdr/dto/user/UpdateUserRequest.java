package com.tchepannou.pdr.dto.user;

@Deprecated
public class UpdateUserRequest {
    //-- Attributes
    private String login;
    private String password;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
