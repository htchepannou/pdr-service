package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.ContactMechanismTypeDao;
import com.tchepannou.pdr.domain.ContactMechanismType;

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
