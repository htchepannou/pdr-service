package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyPostalAddressDao;
import com.tchepannou.pdr.domain.PartyPostalAddress;

import javax.sql.DataSource;

public class PartyPostalAddressDaoImpl extends AbstractPartyContactMechanismDao<PartyPostalAddress> implements PartyPostalAddressDao {
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
