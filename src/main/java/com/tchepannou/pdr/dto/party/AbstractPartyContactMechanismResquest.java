package com.tchepannou.pdr.dto.party;

public abstract class AbstractPartyContactMechanismResquest {
    //-- Attributes
    private boolean noSolicitation;
    private String privacy;
    private String purpose;

    //-- Getter/Setter
    public boolean isNoSolicitation() {
        return noSolicitation;
    }

    public void setNoSolicitation(boolean noSolicitation) {
        this.noSolicitation = noSolicitation;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
