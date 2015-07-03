package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.enums.Gender;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.enums.PartyKind;
import com.tchepannou.pdr.util.DateUtils;

public class PartyResponse {
    //-- Attribute
    private final long id;
    private final PartyKind kind;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final Gender gender;
    private final int heigth;
    private final int weight;
    private final String prefix;
    private final String suffix;
    private final String fromDate;

    //-- Constructor
    private PartyResponse(Builder builder) {
        final Party party = builder.party;
        this.id = party.getId();
        this.kind = party.getKind();
        this.name = party.getName();
        this.firstName = party.getFirstName();
        this.lastName = party.getLastName();
        this.birthDate = DateUtils.asString(party.getBirthDate());
        this.gender = party.getGender();
        this.heigth = party.getHeigth();
        this.weight = party.getWeight();
        this.prefix = party.getPrefix();
        this.suffix = party.getSuffix();
        this.fromDate  = DateUtils.asString(party.getFromDate());
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

    public String getBirthDate() {
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

    public String getFromDate() {
        return fromDate;
    }
}
