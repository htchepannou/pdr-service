package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.ContactMechanismPurposeDao;
import com.tchepannou.pds.domain.ContactMechanismPurpose;

import javax.sql.DataSource;

public class ContactMechanismPurposeDaoImpl extends AbstractPersistentEnumDaoImpl<ContactMechanismPurpose>  implements ContactMechanismPurposeDao {

    public ContactMechanismPurposeDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_contact_mechanism_purpose";
    }

    @Override
    protected ContactMechanismPurpose createPersistenEnum() {
        return new ContactMechanismPurpose();
    }
}
