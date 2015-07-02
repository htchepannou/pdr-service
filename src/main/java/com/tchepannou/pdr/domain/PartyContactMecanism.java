package com.tchepannou.pdr.domain;

import com.tchepannou.pdr.enums.Privacy;

public abstract class PartyContactMecanism extends Persistent{
    //-- Attributes
    private long partyId;
    private long typeId;
    private long purposeId;
    private long contactId;
    private boolean noSolicitation;
    private Privacy privacy;


    //-- Getter/Setter
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

    public long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(long purposeId) {
        this.purposeId = purposeId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public boolean isNoSolicitation() {
        return noSolicitation;
    }

    public void setNoSolicitation(boolean noSolicitation) {
        this.noSolicitation = noSolicitation;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }
}
