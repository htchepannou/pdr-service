package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import com.tchepannou.pdr.service.ContactMechanismTypeService;
import com.tchepannou.pdr.util.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final String toDate;

    private List<PartyElectronicAddressResponse> emailAddresses;
    private List<PartyElectronicAddressResponse> webAddresses;

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
        this.toDate  = DateUtils.asString(party.getToDate());

        addElectronicAddresses(
                builder.electronicAddresses,
                builder.partyElectronicAddresses,
                builder.contactMechanismTypeService,
                builder.contactMechanismPurposeService
        );

    }

    private void addElectronicAddresses (
            final List<ElectronicAddress> electronicAddressesById,
            final List<PartyElectronicAddress> partyElectronicAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ) {
        final Map<Long, ElectronicAddress> electronicAddressMap = electronicAddressesById
                .stream()
                .collect(Collectors.toMap(ElectronicAddress::getId, elt -> elt));

        final Map<PartyElectronicAddress, ElectronicAddress> electronicAddresses = new HashMap<>();
        for (PartyElectronicAddress partyElectronicAddress : partyElectronicAddresses) {
            final ElectronicAddress electronicAddress = electronicAddressMap.get(partyElectronicAddress.getContactId());
            if (electronicAddress != null) {
                electronicAddresses.put(partyElectronicAddress, electronicAddress);
            }
        }

        addElectronicAddresses(
                electronicAddresses,
                contactMechanismTypeService,
                contactMechanismPurposeService
        );
    }

    private void addElectronicAddresses (
            final Map<PartyElectronicAddress, ElectronicAddress> electronicAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ){
        PartyElectronicAddressResponse.Builder builder = new PartyElectronicAddressResponse.Builder();

        for (PartyElectronicAddress key : electronicAddresses.keySet()) {
            ElectronicAddress address = electronicAddresses.get(key);
            ContactMechanismType type = contactMechanismTypeService.findById(key.getTypeId());
            ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(key.getPurposeId());
            if (address == null || type == null || purpose == null) {
                continue;
            }

            builder.withElectronicAddress(address)
                    .withPartyElectronicAddress(key)
                    .withContactMechanismPurpose(purpose)
            ;

            if (type.isEmail()){
                addEmailAddress(builder.build());
            } else if (type.isWeb()) {
                addWebAddress(builder.build());
            }
        }

    }

    private void addEmailAddress (final PartyElectronicAddressResponse address) {
        if (emailAddresses == null){
            emailAddresses = new ArrayList<>();
        }
        emailAddresses.add(address);
    }

    private void addWebAddress (final PartyElectronicAddressResponse address) {
        if (webAddresses == null){
            webAddresses = new ArrayList<>();
        }
        webAddresses.add(address);
    }

    //-- Builder
    public static class Builder {
        private Party party;
        private List<ElectronicAddress> electronicAddresses;
        private List<PartyElectronicAddress> partyElectronicAddresses;
        private ContactMechanismTypeService contactMechanismTypeService;
        private ContactMechanismPurposeService contactMechanismPurposeService;

        public PartyResponse build () {
            Preconditions.checkState(party != null, "party is null");

            return new PartyResponse(this);
        }

        public Builder withParty (final Party party) {
            this.party = party;
            return this;
        }

        public Builder withElectronicAddresses (final List<ElectronicAddress> electronicAddresses) {
            this.electronicAddresses = electronicAddresses;
            return this;
        }

        public Builder withPartyElectronicAddresses (final List<PartyElectronicAddress> partyElectronicAddresses) {
            this.partyElectronicAddresses = partyElectronicAddresses;
            return this;
        }

        public Builder withContactMechanismTypeService (final ContactMechanismTypeService contactMechanismTypeService){
            this.contactMechanismTypeService = contactMechanismTypeService;
            return this;
        }
        
        public Builder withContactMechanismPurposeService (final ContactMechanismPurposeService contactMechanismPurposeService){
            this.contactMechanismPurposeService = contactMechanismPurposeService;
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

    public String getToDate() {
        return toDate;
    }

    public List<PartyElectronicAddressResponse> getEmailAddresses() {
        return emailAddresses;
    }

    public List<PartyElectronicAddressResponse> getWebAddresses() {
        return webAddresses;
    }
}
