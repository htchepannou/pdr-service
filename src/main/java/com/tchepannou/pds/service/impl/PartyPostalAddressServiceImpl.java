package com.tchepannou.pds.service.impl;

import com.tchepannou.pds.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pds.dao.PartyPostalAddressDao;
import com.tchepannou.pds.dao.PostalAddressDao;
import com.tchepannou.pds.domain.*;
import com.tchepannou.pds.dto.party.CreatePartyPostalAddressRequest;
import com.tchepannou.pds.dto.party.PartyPostalAddressRequest;
import com.tchepannou.pds.dto.party.PartyPostalAddressResponse;
import com.tchepannou.pds.service.PartyPostalAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyPostalAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPostalAddress> implements PartyPostalAddressService {
    //-- Attributes
    @Autowired
    private PartyPostalAddressDao dao;

    @Autowired
    private PostalAddressDao postalAddressDao;

    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyPostalAddress> getDao() {
        return dao;
    }


    //-- PartyPostalAddressService override
    @Override
    @Transactional
    public PartyPostalAddressResponse addAddress(
            final long partyId,
            final CreatePartyPostalAddressRequest request
    ) {
        final Party party = findParty(partyId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final ContactMechanismType type = findType(request.getType());
        final PostalAddress address = createPostalAddress(request);
        PartyPostalAddress partyAddress = createAddress(
                party,
                type,
                purpose,
                request,
                address,
                new PartyPostalAddress()
        );

        return toPartyPostalAddressResponse(purpose, address, partyAddress);
    }

    @Override
    @Transactional
    public PartyPostalAddressResponse updateAddress(
            final long partyId,
            final long addressId,
            final PartyPostalAddressRequest request
    ) {
        final Party party = findParty(partyId);
        final PartyPostalAddress partyAddress = findById(addressId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final PostalAddress address = createPostalAddress(request);

        updateAddress(
                party,
                request,
                address,
                partyAddress
        );

        return toPartyPostalAddressResponse(purpose, address, partyAddress);
    }

    @Override
    @Transactional
    public void removeAddress(final long partyId, final long addressId) {
        final Party party = findParty(partyId);
        final PartyPostalAddress partyPostalAddress = findById(addressId);
        removeAddress(party, partyPostalAddress);
    }

    //-- Private
    private PartyPostalAddressResponse toPartyPostalAddressResponse (
            final ContactMechanismPurpose purpose,
            final PostalAddress postalAddress,
            final PartyPostalAddress partyPostalAddress
    ) {
        return new PartyPostalAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPostalAddress(postalAddress)
                .withPartyPostalAddress(partyPostalAddress)
                .build();
    }


    private PostalAddress createPostalAddress (final PartyPostalAddressRequest request) {
        final String hash = PostalAddress.computeHash(
                request.getStreet1(),
                request.getStreet2(),
                request.getCity(),
                request.getStateCode(),
                request.getZipCode(),
                request.getCountryCode()
        );
        PostalAddress postalAddress = postalAddressDao.findByHash(hash);
        if (postalAddress == null) {
            postalAddress = new PostalAddress();
            postalAddress.setStreet1(request.getStreet1());
            postalAddress.setStreet2(request.getStreet2());
            postalAddress.setCity(request.getCity());
            postalAddress.setStateCode(request.getStateCode());
            postalAddress.setZipCode(request.getZipCode());
            postalAddress.setCountryCode(request.getCountryCode());
            postalAddressDao.create(postalAddress);
        }
        return postalAddress;
    }


}
