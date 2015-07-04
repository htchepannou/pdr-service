package com.tchepannou.pdr.dto.auth;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

public class AuthRequest {
    //-- Attributes
    @NotBlank(message = "login")
    private String login;

    @NotBlank(message = "password")
    private String password;

    @Min(value = 1, message = "domainId")
    private long domainId;

    private String remoteIp;
    private String userAgent;

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

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
