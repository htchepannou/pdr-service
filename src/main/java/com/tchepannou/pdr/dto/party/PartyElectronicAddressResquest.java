package com.tchepannou.pdr.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class PartyElectronicAddressResquest extends AbstractPartyContactMechanismResquest{
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
