package com.tchepannou.pdr.domain;

import java.time.LocalDateTime;

public class DomainUser {
    //-- Attribute
    private long id;
    private long domainId;
    private long userId;
    private long roleId;
    private LocalDateTime fromDate;

    //-- Constructor
    public DomainUser (){

    }
    public DomainUser (final long domainId, final long userId, final long roleId) {
        this.domainId = domainId;
        this.userId = userId;
        this.roleId = roleId;
    }

    //-- Getter/Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }
}
