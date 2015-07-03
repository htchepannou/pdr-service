package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.ContactMechanismType;
import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.ContactMechanismTypeService;
import com.tchepannou.pdr.service.ElectronicAddressService;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PartyElectronicAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyElectronicAddress> implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Autowired
    private ElectronicAddressService electronicAddressService;

    @Autowired
    private ContactMechanismTypeService contactMechanismTypeService;


    //-- AbstractPartyContactMechanismServiceImpl overrides
    @Override
    protected AbstractPartyContactMechanismDao<PartyElectronicAddress> getDao() {
        return dao;
    }

    //-- PartyElectronicAddressService overrides
    @Override
    @Transactional
    public PartyElectronicAddress addEmail (final Party party, final String email) {
        final ContactMechanismType type = contactMechanismTypeService.findByName(ContactMechanismType.NAME_EMAIL);

        final ElectronicAddress address = createElectronicAddress(email);

        final PartyElectronicAddress partyAddress = new PartyElectronicAddress();
        partyAddress.setPartyId(party.getId());
        partyAddress.setContactId(address.getId());
        partyAddress.setTypeId(type.getId());
        partyAddress.setContactId(address.getId());

        dao.create(partyAddress);
        return partyAddress;
    }

    private ElectronicAddress createElectronicAddress (final String email) {
        try {

            return electronicAddressService.findByHash(
                    ElectronicAddress.computeHash(email)
            );

        } catch (NotFoundException e) {

            final ElectronicAddress address = new ElectronicAddress();
            address.setAddress(email);
            electronicAddressService.create(address);
            return address;

        }
    }
}
