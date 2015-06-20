package com.tchepannou.pdr.domain;

public class UserLogin extends Persistent {
    //-- Attributes
    private long partyId;
    private String login;
    private String password;

    //-- Getter/Setter
    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(final long partyId) {
        this.partyId = partyId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
