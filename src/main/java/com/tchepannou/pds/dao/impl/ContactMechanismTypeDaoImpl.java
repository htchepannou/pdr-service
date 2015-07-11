package com.tchepannou.pds.dao.impl;

import com.tchepannou.core.dao.impl.AbstractPersistentEnumDaoImpl;
import com.tchepannou.pds.dao.ContactMechanismTypeDao;
import com.tchepannou.pds.domain.ContactMechanismType;

import javax.sql.DataSource;

public class ContactMechanismTypeDaoImpl extends AbstractPersistentEnumDaoImpl<ContactMechanismType> implements ContactMechanismTypeDao {

    public ContactMechanismTypeDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_contact_mechanism_type";
    }

    @Override
    protected ContactMechanismType createPersistenEnum() {
        return new ContactMechanismType();
    }
}
