package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyPhoneDao;
import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.CreatePartyPhoneRequest;
import com.tchepannou.pdr.dto.party.PartyPhoneRequest;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyPhoneService;
import com.tchepannou.pdr.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyPhoneServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPhone> implements PartyPhoneService {
    //-- Attributes
    @Autowired
    private PartyPhoneDao dao;

    @Autowired
    private PhoneService phoneService;
    

    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyPhone> getDao() {
        return dao;
    }
    
    //-- PartyPhoneService overrides
    @Override 
    public PartyPhone addAddress(Party party, CreatePartyPhoneRequest request) {
        final Phone address = createPhone(request);
        return super.addAddress(party, request.getType(), request, new PartyPhone(), address);
    }

    @Override 
    public PartyPhone updateAddress(Party party, long addressId, PartyPhoneRequest request) {
        final PartyPhone partyAddress = findById(addressId);
        final Phone address = createPhone(request);

        return super.updateAddress(party, request, partyAddress, address);
    }

    @Override
    @Transactional
    public void removeAddress(Party party, long addressId) {
        final PartyPhone phone = findById(addressId);
        if (phone.getPartyId() != party.getId()) {
            throw new NotFoundException();
        }

        dao.delete(addressId);
    }
    
    //-- Private
    private Phone createPhone (final PartyPhoneRequest request) {
        final String hash = Phone.computeHash(
                request.getCountryCode(),
                request.getNumber(),
                request.getExtension()
        );
        Phone phone;
        try {
            phone = phoneService.findByHash(hash);
        } catch (NotFoundException e) {     // NOSONAR
            phone = new Phone();
            phone.setCountryCode(request.getCountryCode());
            phone.setNumber(request.getNumber());
            phone.setExtension(request.getExtension());
            phoneService.create(phone);
        }
        return phone;
    }
}
