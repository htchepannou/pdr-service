package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.ContactMechanismPurposeDao;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactMechanismPurposeServiceImpl extends AbstractPersistentEnumServiceImpl<ContactMechanismPurpose> implements ContactMechanismPurposeService {
    @Autowired
    private ContactMechanismPurposeDao dao;

    @Override
    protected AbstractPersistentEnumDao<ContactMechanismPurpose> getDao() {
        return dao;
    }
}
