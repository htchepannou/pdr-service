package com.tchepannou.pds.dto.party;

import com.tchepannou.pds.enums.Privacy;

public abstract class AbstractPartyContactMechanismRequest {
    //-- Attributes
    private boolean noSolicitation;

    @com.tchepannou.core.validator.Enum(enumClass = Privacy.class, message="privacy")
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
