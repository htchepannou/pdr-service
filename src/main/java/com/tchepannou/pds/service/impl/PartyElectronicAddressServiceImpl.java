package com.tchepannou.pds.service.impl;

import com.tchepannou.pds.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pds.dao.ElectronicAddressDao;
import com.tchepannou.pds.dao.PartyElectronicAddressDao;
import com.tchepannou.pds.domain.*;
import com.tchepannou.pds.dto.party.CreatePartyElectronicAddressRequest;
import com.tchepannou.pds.dto.party.PartyElectronicAddressRequest;
import com.tchepannou.pds.dto.party.PartyElectronicAddressResponse;
import com.tchepannou.pds.service.PartyElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyElectronicAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyElectronicAddress> implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Autowired
    private ElectronicAddressDao electronicAddressDao;


    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyElectronicAddress> getDao() {
        return dao;
    }

    //-- PartyElectronicAddressService overrides
    @Override
    @Transactional
    public PartyElectronicAddressResponse addEmail (final long partyId, final String email) {
        final CreatePartyElectronicAddressRequest request = new CreatePartyElectronicAddressRequest();
        request.setType(ContactMechanismType.NAME_EMAIL);
        request.setAddress(email);

        return addAddress(partyId, request);
    }

    @Override
    @Transactional
    public PartyElectronicAddressResponse addAddress(
            final long partyId,
            final CreatePartyElectronicAddressRequest request
    ) {
        final Party party = findParty(partyId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final ContactMechanismType type = findType(request.getType());
        final ElectronicAddress address = createElectronicAddress(request.getAddress());

        PartyElectronicAddress partyAddress = createAddress(
                party,
                type,
                purpose,
                request,
                address,
                new PartyElectronicAddress()
        );

        return toPartyElectronicAddressResponse(purpose, address, partyAddress);
    }

    @Override
    @Transactional
    public PartyElectronicAddressResponse updateAddress(
            final long partyId,
            final long addressId,
            final PartyElectronicAddressRequest request
    ){
        final Party party = findParty(partyId);
        final PartyElectronicAddress partyAddress = findById(addressId);
        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());
        final ElectronicAddress address = createElectronicAddress(request.getAddress());

        updateAddress(
                party,
                request,
                address,
                partyAddress
        );

        return toPartyElectronicAddressResponse(purpose, address, partyAddress);
    }

    @Override
    @Transactional
    public void removeAddress(
            final long partyId,
            final long addressId
    ) {
        final Party party = findParty(partyId);
        final PartyElectronicAddress partyElectronicAddress = findById(addressId);
        removeAddress(party, partyElectronicAddress);
    }


    //-- Private
    private PartyElectronicAddressResponse toPartyElectronicAddressResponse (
            final ContactMechanismPurpose purpose,
            final ElectronicAddress electronicAddress,
            final PartyElectronicAddress partyElectronicAddress
    ) {
        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }


    private ElectronicAddress createElectronicAddress (final String email) {

        ElectronicAddress address = electronicAddressDao.findByHash(
                ElectronicAddress.computeHash(email)
        );

        if (address == null) {

            address = new ElectronicAddress();
            address.setAddress(email);
            electronicAddressDao.create(address);

        }
        return address;
    }
}
