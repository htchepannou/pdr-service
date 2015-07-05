package com.tchepannou.pdr.dto.party;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.hibernate.validator.constraints.NotBlank;

public class CreatePartyRequest {
    //-- Attributes
    @NotBlank(message = "kind")
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

    @NotBlank(message = "name")
    public String getName() {
        if (Strings.isNullOrEmpty(name)){
            name = Joiner
                    .on(' ')
                    .skipNulls()
                    .join(firstName, lastName);
        }
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
