package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.PartyRoleTypeDao;
import com.tchepannou.pds.domain.PartyRoleType;

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
