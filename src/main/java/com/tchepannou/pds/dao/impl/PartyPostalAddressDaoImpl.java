package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.PartyPostalAddressDao;
import com.tchepannou.pds.domain.PartyPostalAddress;

import javax.sql.DataSource;

public class PartyPostalAddressDaoImpl extends AbstractPartyContactMechanismDaoImpl<PartyPostalAddress> implements PartyPostalAddressDao {
    public PartyPostalAddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getContactColumn() {
        return "paddress_fk";
    }

    @Override
    protected PartyPostalAddress createPartyContactMechanism() {
        return new PartyPostalAddress();
    }
}
