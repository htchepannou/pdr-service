package com.tchepannou.pdr.dto.user;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.UserStatus;
import com.tchepannou.pdr.domain.UserStatusCode;

import java.io.Serializable;
import java.util.Date;

public class UserStatusResponse implements Serializable {
    //-- Attribute
    private long id;
    private long codeId;
    private String codeName;
    private String comment;
    private Date date;

    //-- Constructor
    public UserStatusResponse(final Builder builder){
        final UserStatus userStatus = builder.userStatus;
        final UserStatusCode statusCode = builder.statusCode;

        this.id = userStatus.getId();
        this.codeId = statusCode.getId();
        this.codeName = statusCode.getName();
        this.date = userStatus.getDate();
        this.comment = userStatus.getComment();
    }

    //-- Getter
    public long getId() {
        return id;
    }

    public long getCodeId() {
        return codeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    //-- Builder
    public static class Builder {
        private UserStatus userStatus;
        private UserStatusCode statusCode;

        public UserStatusResponse build () {
            Preconditions.checkState(userStatus != null, "userStatus not set");
            Preconditions.checkState(statusCode != null, "userStatusCode not set");
            Preconditions.checkState(statusCode.getId() == userStatus.getStatusCodeId(), "userStatusCode is invalid");

            return new UserStatusResponse(this);
        }
        
        public Builder withUserStatus(final UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }
        
        public Builder withStatusCode (final UserStatusCode userStatusCode) {
            this.statusCode = userStatusCode;
            return this;
        }
    }
}
