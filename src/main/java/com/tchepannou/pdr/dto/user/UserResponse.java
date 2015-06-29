package com.tchepannou.pdr.dto.user;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserResponse implements Serializable {
    //-- Attribute
    private long id;
    private long partyId;
    private String login;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    //-- Constructor
    public UserResponse(final Builder builder){
        final User user = builder.user;

        this.id = user.getId();
        this.partyId = user.getPartyId();

        this.login = user.getLogin();
        this.status = user.getStatus().name();
        this.fromDate = user.getFromDate();
        this.toDate = user.getToDate();
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

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
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
