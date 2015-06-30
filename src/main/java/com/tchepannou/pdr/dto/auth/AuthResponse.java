package com.tchepannou.pdr.dto.auth;

import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.util.DateUtils;

public class AuthResponse {
    //-- Attributes
    private long id;
    private long userId;
    private long domainId;
    private String fromDate;
    private String toDate;
    private String expiryDate;
    private boolean expired;


    //-- Constructor
    private AuthResponse (final Builder builder){
        AccessToken token = builder.token;
        this.id = token.getId();
        this.userId = token.getUserId();
        this.domainId = token.getDomainId();
        this.expired = token.isExpired();
        this.fromDate = DateUtils.asString(token.getFromDate());
        this.toDate = DateUtils.asString(token.getToDate());
        this.expiryDate = DateUtils.asString(token.getExpiryDate());
    }

    //-- Getter/Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public long getDomainId() {
        return domainId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return expired;
    }

    //-- Builder
    public static class Builder {
        private AccessToken token;

        public AuthResponse build (){
            return new AuthResponse(this);
        }

        public Builder withAccessToken (AccessToken token) {
            this.token = token;
            return this;
        }
    }
}
