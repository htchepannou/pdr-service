package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyPostalAddressDao;
import com.tchepannou.pdr.domain.PartyPostalAddress;
import com.tchepannou.pdr.service.PartyPostalAddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyPostalAddressServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPostalAddress> implements PartyPostalAddressService {
    @Autowired
    private PartyPostalAddressDao dao;

    @Override
    protected AbstractPartyContactMechanismDao<PartyPostalAddress> getDao() {
        return dao;
    }
}
