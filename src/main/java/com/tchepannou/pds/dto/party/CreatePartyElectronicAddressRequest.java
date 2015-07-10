package com.tchepannou.pds.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class CreatePartyElectronicAddressRequest extends PartyElectronicAddressRequest {
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
