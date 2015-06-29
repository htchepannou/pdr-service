package com.tchepannou.pdr.dto.user;

import com.tchepannou.pdr.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GetUserResponse implements Serializable {
    //-- Attribute
    private long id;
    private long partyId;
    private String login;
    private String status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;


    //-- Constructor
    public GetUserResponse(final User user){
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
}
