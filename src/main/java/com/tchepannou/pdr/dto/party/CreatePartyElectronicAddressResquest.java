package com.tchepannou.pdr.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class CreatePartyElectronicAddressResquest extends PartyElectronicAddressResquest{
    //-- Attributes
    @NotBlank (message = "type")
    private String type;

    //-- Getter/Setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}