package com.tchepannou.pdr.dto.user;

public class CreateUserRequest {
    //-- Attributes
    private long partyId;
    private String login;
    private String password;

    //-- Getter/Setter
    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

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
}
