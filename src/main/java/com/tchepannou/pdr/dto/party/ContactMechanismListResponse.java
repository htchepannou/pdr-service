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
    private List<PartyPhoneResponse> phones;


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

        addPhones(
                builder.phones,
                builder.partyPhones,
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
        private List<Phone> phones;
        private List<PartyPhone> partyPhones;
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

        public Builder withPhones (final List<Phone> phones) {
            this.phones = phones;
            return this;
        }

        public Builder withPartyPhones (final List<PartyPhone> partyPhones) {
            this.partyPhones = partyPhones;
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

    public List<PartyPhoneResponse> getPhones() {
        return phones;
    }

    //-- Private
    private void addElectronicAddresses (
            final List<ElectronicAddress> electronicAddresses,
            final List<PartyElectronicAddress> partyElectronicAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ) {
        if (electronicAddresses == null) {
            return;
        }

        final Map<Long, ElectronicAddress> electronicAddressMap = electronicAddresses
                .stream()
                .collect(Collectors.toMap(ElectronicAddress::getId, elt -> elt));

        final Map<PartyElectronicAddress, ElectronicAddress> electronicAddressesByParty = new HashMap<>();
        for (PartyElectronicAddress partyElectronicAddress : partyElectronicAddresses) {
            final ElectronicAddress electronicAddress = electronicAddressMap.get(partyElectronicAddress.getContactId());
            if (electronicAddress != null) {
                electronicAddressesByParty.put(partyElectronicAddress, electronicAddress);
            }
        }

        addElectronicAddresses(
                electronicAddressesByParty,
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
            final List<PostalAddress> postalAddresses,
            final List<PartyPostalAddress> partyPostalAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ) {
        if (postalAddresses == null) {
            return;
        }

        final Map<Long, PostalAddress> electronicAddressMap = postalAddresses
                .stream()
                .collect(Collectors.toMap(PostalAddress::getId, elt -> elt));

        final Map<PartyPostalAddress, PostalAddress> postalAddressesByParty = new HashMap<>();
        for (PartyPostalAddress partyPostalAddress : partyPostalAddresses) {
            final PostalAddress electronicAddress = electronicAddressMap.get(partyPostalAddress.getContactId());
            if (electronicAddress != null) {
                postalAddressesByParty.put(partyPostalAddress, electronicAddress);
            }
        }

        addPostalAddresses(
                postalAddressesByParty,
                contactMechanismTypeService,
                contactMechanismPurposeService
        );
    }  
    
    private void addPostalAddresses (
            final Map<PartyPostalAddress, PostalAddress> postalAddresses,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ){
        PartyPostalAddressResponse.Builder builder = new PartyPostalAddressResponse.Builder();

        for (PartyPostalAddress key : postalAddresses.keySet()) {
            PostalAddress address = postalAddresses.get(key);
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

    private void addPhones (
            final List<Phone> phones,
            final List<PartyPhone> partyPhones,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ) {
        if (phones == null) {
            return;
        }

        final Map<Long, Phone> electronicAddressMap = phones
                .stream()
                .collect(Collectors.toMap(Phone::getId, elt -> elt));

        final Map<PartyPhone, Phone> phonesByParty = new HashMap<>();
        for (PartyPhone partyPhone : partyPhones) {
            final Phone electronicAddress = electronicAddressMap.get(partyPhone.getContactId());
            if (electronicAddress != null) {
                phonesByParty.put(partyPhone, electronicAddress);
            }
        }

        addPhones(
                phonesByParty,
                contactMechanismTypeService,
                contactMechanismPurposeService
        );
    }

    private void addPhones (
            final Map<PartyPhone, Phone> phones,
            final ContactMechanismTypeService contactMechanismTypeService,
            final ContactMechanismPurposeService contactMechanismPurposeService
    ){
        PartyPhoneResponse.Builder builder = new PartyPhoneResponse.Builder();

        for (PartyPhone key : phones.keySet()) {
            Phone address = phones.get(key);
            ContactMechanismType type = contactMechanismTypeService.findById(key.getTypeId());
            ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(key.getPurposeId());
            if (address == null || type == null || purpose == null) {
                continue;
            }

            builder.withPhone(address)
                    .withPartyPhone(key)
                    .withContactMechanismPurpose(purpose)
            ;

            addPhone(builder.build());
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

    private void addPhone (final PartyPhoneResponse phone) {
        if (phones == null){
            phones = new ArrayList<>();
        }
        phones.add(phone);
    }
}
