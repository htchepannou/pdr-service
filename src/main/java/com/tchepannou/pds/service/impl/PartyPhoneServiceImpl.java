package com.tchepannou.pds.service.impl;

import com.tchepannou.pds.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pds.dao.PartyPhoneDao;
import com.tchepannou.pds.dao.PhoneDao;
import com.tchepannou.pds.domain.*;
import com.tchepannou.pds.dto.party.CreatePartyPhoneRequest;
import com.tchepannou.pds.dto.party.PartyPhoneRequest;
import com.tchepannou.pds.dto.party.PartyPhoneResponse;
import com.tchepannou.pds.service.PartyPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyPhoneServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPhone> implements PartyPhoneService {
    //-- Attributes
    @Autowired
    private PartyPhoneDao dao;

    @Autowired
    private PhoneDao phoneDao;
    

    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyPhone> getDao() {
        return dao;
    }
    
    //-- PartyPhoneService overrides
    @Override 
    public PartyPhoneResponse addAddress(
            final long partyId,
            final CreatePartyPhoneRequest request
    ) {
        final Party party = findParty(partyId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final ContactMechanismType type = findType(request.getType());
        final Phone address = createPhone(request);
        
        PartyPhone partyAddress = createAddress(
                party,
                type,
                purpose,
                request,
                address,
                new PartyPhone()
        );

        return toPartyPhoneResponse(purpose, address, partyAddress);
    }

    @Override 
    public PartyPhoneResponse updateAddress(
            final long partyId,
            final long addressId,
            final PartyPhoneRequest request
    ) {
        final Party party = findParty(partyId);
        final PartyPhone partyAddress = findById(addressId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final Phone address = createPhone(request);

        updateAddress(
                party,
                request,
                address,
                partyAddress
        );

        return toPartyPhoneResponse(purpose, address, partyAddress);
    }

    @Override
    @Transactional
    public void removeAddress(long partyId, long addressId) {
        final Party party = findParty(partyId);
        final PartyPhone partyPhone = findById(addressId);
        removeAddress(party, partyPhone);
    }
    
    //-- Private
    private PartyPhoneResponse toPartyPhoneResponse (
            final ContactMechanismPurpose purpose,
            final Phone phone,
            final PartyPhone partyPhone
    ) {
        return new PartyPhoneResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPhone(phone)
                .withPartyPhone(partyPhone)
                .build();
    }

    private Phone createPhone (final PartyPhoneRequest request) {
        final String hash = Phone.computeHash(
                request.getCountryCode(),
                request.getNumber(),
                request.getExtension()
        );
        Phone phone = phoneDao.findByHash(hash);
        if (phone == null) {
            phone = new Phone();
            phone.setCountryCode(request.getCountryCode());
            phone.setNumber(request.getNumber());
            phone.setExtension(request.getExtension());
            phoneDao.create(phone);
        }
        return phone;
    }
}
