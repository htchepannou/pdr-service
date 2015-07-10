package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.PartyPhoneDao;
import com.tchepannou.pds.domain.PartyPhone;

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
