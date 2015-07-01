package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.PartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyElectronicAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyElectronicAddress> implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Override
    protected PartyContactMechanismDao<PartyElectronicAddress> getDao() {
        return dao;
    }
}
