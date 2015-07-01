package com.tchepannou.pdr.dto.party;

import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import com.tchepannou.pdr.service.ContactMechanismTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactMechanismListResponse {
    //-- Attributes
    private List<PartyElectronicAddressResponse> emailAddresses;
    private List<PartyElectronicAddressResponse> webAddresses;
    private List<PartyPostalAddressResponse> postalAddresses;


    //-- Constructor
    public ContactMechanismListResponse(Builder builder){
        addElectronicAddresses(
                builder.electronicAddresses,
                builder.partyElectronicAddresses,
                builder.contactMechanismTypeService,
                builder.contactMechanismPurposeService
        );

        addPostalAddresses(
                builder.postalAddresses,
                builder.partyPostalAddresses,
                builder.contactMechanismTypeService,
                builder.contactMechanismPurposeService
        );
    }
    
    //-- Builder
    public static class Builder {
        private List<ElectronicAddress> electronicAddresses;
        private List<PartyElectronicAddress> partyElectronicAddresses;
        private List<PostalAddress> postalAddresses;
        private List<PartyPostalAddress> partyPostalAddresses;
        private ContactMechanismTypeService contactMechanismTypeService;
        private ContactMechanismPurposeService contactMechanismPurposeService;

        public ContactMechanismListResponse build () {
            return new ContactMechanismListResponse(this);
        }

        public Builder withElectronicAddresses (final List<ElectronicAddress> electronicAddresses) {
            this.electronicAddresses = electronicAddresses;
            return this;
        }

        public Builder withPartyElectronicAddresses (final List<PartyElectronicAddress> partyElectronicAddresses) {
            this.partyElectronicAddresses = partyElectronicAddresses;
            return this;
        }

        public Builder withPostalAddresses (final List<PostalAddress> postalAddresses) {
            this.postalAddresses = postalAddresses;
            return this;
        }

        public Builder withPartyPostalAddresses (final List<PartyPostalAddress> partyPostalAddresses) {
            this.partyPostalAddresses = partyPostalAddresses;
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
    public List<PartyElectronicAddressResponse> getEmailAddresses() {
        return emailAddresses;
    }

    public List<PartyElectronicAddressResponse> getWebAddresses() {
        return webAddresses;
    }

    public List<PartyPostalAddressResponse> getPostalAddresses() {
        return postalAddresses;
    }

    //-- Private
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

    private void addPostalAddresses (
            final List<PostalAddress> electronicAddressesById,
            final List<PartyPostalAddress> partyPostalAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ) {
        final Map<Long, PostalAddress> electronicAddressMap = electronicAddressesById
                .stream()
                .collect(Collectors.toMap(PostalAddress::getId, elt -> elt));

        final Map<PartyPostalAddress, PostalAddress> electronicAddresses = new HashMap<>();
        for (PartyPostalAddress partyPostalAddress : partyPostalAddresses) {
            final PostalAddress electronicAddress = electronicAddressMap.get(partyPostalAddress.getContactId());
            if (electronicAddress != null) {
                electronicAddresses.put(partyPostalAddress, electronicAddress);
            }
        }

        addPostalAddresses(
                electronicAddresses,
                contactMechanismTypeService,
                contactMechanismPurposeService
        );
    }  
    
    private void addPostalAddresses (
            final Map<PartyPostalAddress, PostalAddress> electronicAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ){
        PartyPostalAddressResponse.Builder builder = new PartyPostalAddressResponse.Builder();

        for (PartyPostalAddress key : electronicAddresses.keySet()) {
            PostalAddress address = electronicAddresses.get(key);
            ContactMechanismType type = contactMechanismTypeService.findById(key.getTypeId());
            ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(key.getPurposeId());
            if (address == null || type == null || purpose == null) {
                continue;
            }

            builder.withPostalAddress(address)
                    .withPartyPostalAddress(key)
                    .withContactMechanismPurpose(purpose)
            ;

            addPostalAddress(builder.build());
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

    private void addPostalAddress (final PartyPostalAddressResponse address) {
        if (postalAddresses == null){
            postalAddresses = new ArrayList<>();
        }
        postalAddresses.add(address);
    }

}
