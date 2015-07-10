package com.tchepannou.pds.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class PartyElectronicAddressRequest extends AbstractPartyContactMechanismRequest {
    //-- Attributes
    @NotBlank (message = "address")
    private String address;

    //-- Getter/Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
