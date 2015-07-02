package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyPhoneDao;
import com.tchepannou.pdr.domain.PartyPhone;

import javax.sql.DataSource;

public class PartyPhoneDaoImpl extends AbstractPartyContactMechanismDaoImpl<PartyPhone> implements PartyPhoneDao {
    public PartyPhoneDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getContactColumn() {
        return "phone_fk";
    }

    @Override
    protected PartyPhone createPartyContactMechanism() {
        return new PartyPhone();
    }
}
