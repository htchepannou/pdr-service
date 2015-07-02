package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.dao.PartyPhoneDao;
import com.tchepannou.pdr.domain.PartyPhone;
import com.tchepannou.pdr.service.PartyPhoneService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyPhoneServiceImpl extends AbstractPartyContactMechanismServiceImpl<PartyPhone> implements PartyPhoneService {
    @Autowired
    private PartyPhoneDao dao;

    @Override
    protected AbstractPartyContactMechanismDao<PartyPhone> getDao() {
        return dao;
    }
}
