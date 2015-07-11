package com.tchepannou.pds.service.impl;

import com.tchepannou.core.exception.NotFoundException;
import com.tchepannou.pds.dao.*;
import com.tchepannou.pds.domain.*;
import com.tchepannou.pds.dto.party.ContactMechanismListResponse;
import com.tchepannou.pds.dto.party.PartyElectronicAddressResponse;
import com.tchepannou.pds.dto.party.PartyPhoneResponse;
import com.tchepannou.pds.dto.party.PartyPostalAddressResponse;
import com.tchepannou.pds.service.PartyContactMechanismService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartyContactMechanismServiceImpl implements PartyContactMechanismService {
    //-- Attribute
    @Autowired
    private PartyDao partyDao;
    
    @Autowired
    private ElectronicAddressDao electronicAddressDao;
    
    @Autowired
    private PostalAddressDao postalAddressDao;
    
    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PartyElectronicAddressDao partyElectronicAddressDao;

    @Autowired
    private PartyPhoneDao partyPhoneDao;
    
    @Autowired
    private PartyPostalAddressDao partyPostalAddressDao;
    
    @Autowired
    private ContactMechanismPurposeDao contactMechanismPurposeDao;

    @Autowired
    private ContactMechanismTypeDao contactMechanismTypeDao;
    
    //-- Public
    @Override
    public ContactMechanismListResponse findByParty(long partyId) {
        final Party party = findParty(partyId);
        
        final Map<Long, ContactMechanismPurpose> purposes = contactMechanismPurposeDao.findAll()
                .stream()
                .collect(Collectors.toMap(ContactMechanismPurpose::getId, elt -> elt));


        final Map<Long, ContactMechanismType> types = contactMechanismTypeDao.findAll()
                .stream()
                .collect(Collectors.toMap(ContactMechanismType::getId, elt -> elt));
        
        final ContactMechanismListResponse.Builder builder = new ContactMechanismListResponse.Builder();
        
        addElectronicAddresses(party, purposes, types, builder);
        addPostalAddresses(party, purposes, builder);
        addPhones(party, purposes, builder);

        return builder.build();
    }



    //-- Private
    private Party findParty(final long id) {
        Party party = partyDao.findById(id);
        if (party == null){
            throw new NotFoundException(id, Party.class);
        }
        return party;
    }

    private void addElectronicAddresses (
            final Party party,
            final Map<Long, ContactMechanismPurpose> purposes,
            final Map<Long, ContactMechanismType> types,
            final ContactMechanismListResponse.Builder builder
    ) {
        final List<PartyElectronicAddress> partyElectronicAddresses = partyElectronicAddressDao.findByParty(party.getId());

        final List<Long> electronicAddressIds = partyElectronicAddresses.stream()
                .map(PartyElectronicAddress::getContactId)
                .collect(Collectors.toList());

        final Map<Long, ElectronicAddress> electronicAddressMap = electronicAddressDao
                .findByIds(electronicAddressIds)                
                .stream()
                .collect(Collectors.toMap(ElectronicAddress::getId, elt -> elt));
        
        PartyElectronicAddressResponse.Builder addressBuilder = new PartyElectronicAddressResponse.Builder();
        for (PartyElectronicAddress partyElectronicAddress : partyElectronicAddresses) {
            final PartyElectronicAddressResponse address = addressBuilder
                        .withPartyElectronicAddress(partyElectronicAddress)
                        .withElectronicAddress(electronicAddressMap.get(partyElectronicAddress.getContactId()))
                        .withContactMechanismPurpose(purposes.get(partyElectronicAddress.getPurposeId()))
                        .build();

            ContactMechanismType type = types.get(partyElectronicAddress.getTypeId());
            if (ContactMechanismType.NAME_EMAIL.equals(type.getName())){
                builder.addEmailAddress(address);
            } else {
                builder.addWebAddress(address);
            }
        }
    }

    private void addPostalAddresses (
            final Party party,
            final Map<Long, ContactMechanismPurpose> purposes,
            final ContactMechanismListResponse.Builder builder
    ) {
        final List<PartyPostalAddress> partyPostalAddresses = partyPostalAddressDao.findByParty(party.getId());

        final List<Long> postalAddressIds = partyPostalAddresses.stream()
                .map(PartyPostalAddress::getContactId)
                .collect(Collectors.toList());

        final Map<Long, PostalAddress> postalAddressMap = postalAddressDao
                .findByIds(postalAddressIds)
                .stream()
                .collect(Collectors.toMap(PostalAddress::getId, elt -> elt));

        PartyPostalAddressResponse.Builder addressBuilder = new PartyPostalAddressResponse.Builder();
        for (PartyPostalAddress partyPostalAddress : partyPostalAddresses) {
            final PartyPostalAddressResponse address = addressBuilder
                    .withPartyPostalAddress(partyPostalAddress)
                    .withPostalAddress(postalAddressMap.get(partyPostalAddress.getContactId()))
                    .withContactMechanismPurpose(purposes.get(partyPostalAddress.getPurposeId()))
                    .build();

            builder.addPostalAddress(address);
        }
    }

    private void addPhones(
            final Party party,
            final Map<Long, ContactMechanismPurpose> purposes,
            final ContactMechanismListResponse.Builder builder
    ) {
        final List<PartyPhone> partyPhonees = partyPhoneDao.findByParty(party.getId());

        final List<Long> phoneIds = partyPhonees.stream()
                .map(PartyPhone::getContactId)
                .collect(Collectors.toList());

        final Map<Long, Phone> phoneMap = phoneDao
                .findByIds(phoneIds)
                .stream()
                .collect(Collectors.toMap(Phone::getId, elt -> elt));

        PartyPhoneResponse.Builder addressBuilder = new PartyPhoneResponse.Builder();
        for (PartyPhone partyPhone : partyPhonees) {
            final PartyPhoneResponse address = addressBuilder
                    .withPartyPhone(partyPhone)
                    .withPhone(phoneMap.get(partyPhone.getContactId()))
                    .withContactMechanismPurpose(purposes.get(partyPhone.getPurposeId()))
                    .build();

            builder.addPhone(address);
        }
    }
}
