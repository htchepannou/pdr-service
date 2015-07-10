package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.PartyElectronicAddressDao;
import com.tchepannou.pds.domain.PartyElectronicAddress;

import javax.sql.DataSource;

public class PartyElectronicAddressDaoImpl extends AbstractPartyContactMechanismDaoImpl<PartyElectronicAddress> implements PartyElectronicAddressDao {
    public PartyElectronicAddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getContactColumn() {
        return "eaddress_fk";
    }

    @Override
    protected PartyElectronicAddress createPartyContactMechanism() {
        return new PartyElectronicAddress();
    }


}
