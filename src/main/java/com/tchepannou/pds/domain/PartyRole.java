package com.tchepannou.pds.domain;

import com.tchepannou.core.domain.Persistent;

import java.util.Date;

public class PartyRole extends Persistent {
    private long partyId;
    private long typeId;
    private Date fromDate;

    public long getPartyId() {
        return partyId;
    }

    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
}
