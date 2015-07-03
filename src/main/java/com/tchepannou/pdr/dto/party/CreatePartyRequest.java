package com.tchepannou.pdr.dto.party;

import org.hibernate.validator.constraints.NotBlank;

public class CreatePartyRequest {
    //-- Attributes
    @NotBlank
    private String kind;

    private String name;
    private String firstName;
    private String lastName;

    //-- Getter/Setter
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
