package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyPostalAddressDao;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyPostalAddress;
import com.tchepannou.pdr.domain.PostalAddress;
import com.tchepannou.pdr.dto.party.CreatePartyPostalAddressRequest;
import com.tchepannou.pdr.dto.party.PartyPostalAddressRequest;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyPostalAddressService;
import com.tchepannou.pdr.service.PostalAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyPostalAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPostalAddress> implements PartyPostalAddressService {
    //-- Attributes
    @Autowired
    private PartyPostalAddressDao dao;

    @Autowired
    private PostalAddressService postalAddressService;

    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyPostalAddress> getDao() {
        return dao;
    }
    
    
    //-- PartyPostalAddressService override
    @Override 
    @Transactional
    public PartyPostalAddress addAddress(Party party, CreatePartyPostalAddressRequest request) {
        final PostalAddress address = createPostalAddress(request);
        return super.addAddress(party, request.getType(), request, new PartyPostalAddress(), address);
    }

    @Override
    @Transactional
    public PartyPostalAddress updateAddress(Party party, long addressId, PartyPostalAddressRequest request) {
        final PartyPostalAddress partyAddress = findById(addressId);
        final PostalAddress address = createPostalAddress(request);

        return super.updateAddress(party, request, partyAddress, address);
    }

    @Override
    @Transactional
    public void removeAddress(Party party, long addressId) {
        final PartyPostalAddress address = findById(addressId);
        super.removeAddress(party, address);
    }

    //-- Private
    private PostalAddress createPostalAddress (final PartyPostalAddressRequest request) {
        final String hash = PostalAddress.computeHash(
                request.getStreet1(),
                request.getStreet2(),
                request.getCity(),
                request.getStateCode(),
                request.getZipCode(),
                request.getCountryCode()
        );
        PostalAddress postalAddress;
        try {
            postalAddress = postalAddressService.findByHash(hash);
        } catch (NotFoundException e) {     // NOSONAR
            postalAddress = new PostalAddress();
            postalAddress.setStreet1(request.getStreet1());
            postalAddress.setStreet2(request.getStreet2());
            postalAddress.setCity(request.getCity());
            postalAddress.setStateCode(request.getStateCode());
            postalAddress.setZipCode(request.getZipCode());
            postalAddress.setCountryCode(request.getCountryCode());
            postalAddressService.create(postalAddress);
        }
        return postalAddress;
    }    
}
