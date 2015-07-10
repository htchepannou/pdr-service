package com.tchepannou.pds.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class PartyPhoneRequest extends AbstractPartyContactMechanismRequest {
    //-- Attributes
    private String countryCode;

    @NotBlank(message = "number")
    private String number;

    private String extension;

    //-- Getter/Setter
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
