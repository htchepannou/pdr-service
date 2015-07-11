package com.tchepannou.pds.service.impl;

import com.tchepannou.core.dao.AbstractPersistentEnumDao;
import com.tchepannou.core.service.impl.AbstractPersistentEnumServiceImpl;
import com.tchepannou.pds.dao.ContactMechanismPurposeDao;
import com.tchepannou.pds.domain.ContactMechanismPurpose;
import com.tchepannou.pds.service.ContactMechanismPurposeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactMechanismPurposeServiceImpl extends AbstractPersistentEnumServiceImpl<ContactMechanismPurpose> implements ContactMechanismPurposeService {
    @Autowired
    private ContactMechanismPurposeDao dao;

    @Override
    protected AbstractPersistentEnumDao<ContactMechanismPurpose> getDao() {
        return dao;
    }
}
