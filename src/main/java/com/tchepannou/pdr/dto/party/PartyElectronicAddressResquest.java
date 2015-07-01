package com.tchepannou.pdr.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class PartyElectronicAddressResquest {
    //-- Attributes
    private boolean noSolicitation;
    private String privacy;
    private String purpose;

    @NotBlank (message = "address")
    private String address;

    //-- Getter/Setter
    public boolean isNoSolicitation() {
        return noSolicitation;
    }

    public void setNoSolicitation(boolean noSolicitation) {
        this.noSolicitation = noSolicitation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
