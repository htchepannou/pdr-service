package com.tchepannou.pdr.dto.user;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.util.DateUtils;

import java.io.Serializable;

public class UserResponse implements Serializable {
    //-- Attribute
    private long id;
    private long partyId;
    private String login;
    private String status;
    private String fromDate;
    private String toDate;

    //-- Constructor
    public UserResponse(final Builder builder){
        final User user = builder.user;

        this.id = user.getId();
        this.partyId = user.getPartyId();

        this.login = user.getLogin();
        this.status = user.getStatus().name();
        this.fromDate = DateUtils.asString(user.getFromDate());
        this.toDate = DateUtils.asString(user.getToDate());
    }

    //-- Getter
    public long getId() {
        return id;
    }

    public long getPartyId() {
        return partyId;
    }

    public String getLogin() {
        return login;
    }

    public String getStatus() {
        return status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    //-- Builder
    public static class Builder {
        private User user;

        public UserResponse build () {
            Preconditions.checkState(user != null, "user not set");

            return new UserResponse(this);
        }
        public Builder withUser (final User user) {
            this.user = user;
            return this;
        }
    }
}
