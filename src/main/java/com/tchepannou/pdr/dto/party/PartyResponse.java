package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.Gender;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyKind;

import java.time.LocalDate;

public class PartyResponse {
    //-- Attribute
    private long id;
    private PartyKind kind;
    private String name;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private int heigth;
    private int weight;
    private String prefix;
    private String suffix;


    //-- Constructor
    private PartyResponse(Builder builder) {
        final Party party = builder.party;
        this.id = party.getId();
        this.kind = party.getKind();
        this.name = party.getName();
        this.firstName = party.getFirstName();
        this.lastName = party.getLastName();
        this.birthDate = party.getBirthDate();
        this.gender = party.getGender();
        this.heigth = party.getHeigth();
        this.weight = party.getWeight();
        this.prefix = party.getPrefix();
        this.suffix = party.getSuffix();
    }


    //-- Builder
    public static class Builder {
        private Party party;

        public PartyResponse build () {
            Preconditions.checkState(party != null, "party is null");

            return new PartyResponse(this);
        }

        public Builder withParty (final Party party) {
            this.party = party;
            return this;
        }
    }

    //-- Getter
    public long getId() {
        return id;
    }

    public PartyKind getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWeight() {
        return weight;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
