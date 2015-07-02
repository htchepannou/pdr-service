package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.RoleDao;
import com.tchepannou.pdr.domain.Role;

import javax.sql.DataSource;

public class RoleDaoImpl extends AbstractPersistentEnumDaoImpl<Role> implements RoleDao {

    public RoleDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_role";
    }

    @Override
    protected Role createPersistenEnum() {
        return new Role();
    }
}
