package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyRoleTypeDao;
import com.tchepannou.pdr.domain.PartyRoleType;

import javax.sql.DataSource;

public class PartyRoleTypeDaoImpl extends AbstractPersistentEnumDaoImpl<PartyRoleType> implements PartyRoleTypeDao {

    public PartyRoleTypeDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_party_role_type";
    }

    @Override
    protected PartyRoleType createPersistenEnum() {
        return new PartyRoleType();
    }
}
