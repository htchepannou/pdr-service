package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.CreatePartyElectronicAddressRequest;
import com.tchepannou.pdr.dto.party.PartyElectronicAddressRequest;
import com.tchepannou.pdr.enums.Privacy;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.ElectronicAddressService;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyElectronicAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyElectronicAddress> implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Autowired
    private ElectronicAddressService electronicAddressService;


    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyElectronicAddress> getDao() {
        return dao;
    }

    //-- PartyElectronicAddressService overrides
    @Override
    @Transactional
    public PartyElectronicAddress addEmail (final Party party, final String email) {
        final CreatePartyElectronicAddressRequest request = new CreatePartyElectronicAddressRequest();
        request.setType(ContactMechanismType.NAME_EMAIL);
        request.setAddress(email);

        return addAddress(party, request);
    }

    @Override
    @Transactional
    public PartyElectronicAddress addAddress(Party party, CreatePartyElectronicAddressRequest request) {
        final ElectronicAddress address = createElectronicAddress(request.getAddress());

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = contactMechanismTypeService.findByName(request.getType());

        final PartyElectronicAddress partyElectronicAddress = new PartyElectronicAddress();
        partyElectronicAddress.setPartyId(party.getId());
        partyElectronicAddress.setTypeId(type.getId());
        partyElectronicAddress.setContactId(address.getId());
        partyElectronicAddress.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyElectronicAddress.setNoSolicitation(request.isNoSolicitation());
        partyElectronicAddress.setPurposeId(purpose == null ? 0 : purpose.getId());

        dao.create(partyElectronicAddress);

        return partyElectronicAddress;
    }

    @Override
    @Transactional
    public PartyElectronicAddress updateAddress(Party party, long addressId, PartyElectronicAddressRequest request){
        final PartyElectronicAddress partyElectronicAddress = findById(addressId);
        if (partyElectronicAddress.getPartyId() != party.getId()) {
            throw new NotFoundException();
        }

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ElectronicAddress address = createElectronicAddress(request.getAddress());

        partyElectronicAddress.setContactId(address.getId());
        partyElectronicAddress.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyElectronicAddress.setNoSolicitation(request.isNoSolicitation());
        partyElectronicAddress.setPurposeId(purpose == null ? 0 : purpose.getId());

        dao.update(partyElectronicAddress);

        return partyElectronicAddress;
    }

    @Override
    @Transactional
    public void removeAddress(Party party, long addressId) {
        final PartyElectronicAddress partyElectronicAddress = findById(addressId);
        removeAddress(party, partyElectronicAddress);
    }


    //-- Private
    private ElectronicAddress createElectronicAddress (final String email) {
        try {

            return electronicAddressService.findByHash(
                    ElectronicAddress.computeHash(email)
            );

        } catch (NotFoundException e) {     // NOSONAR

            final ElectronicAddress address = new ElectronicAddress();
            address.setAddress(email);
            electronicAddressService.create(address);
            return address;

        }
    }
}
