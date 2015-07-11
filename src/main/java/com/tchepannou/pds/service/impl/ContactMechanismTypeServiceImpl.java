package com.tchepannou.pds.service.impl;

import com.tchepannou.core.dao.AbstractPersistentEnumDao;
import com.tchepannou.core.service.impl.AbstractPersistentEnumServiceImpl;
import com.tchepannou.pds.dao.ContactMechanismTypeDao;
import com.tchepannou.pds.domain.ContactMechanismType;
import com.tchepannou.pds.service.ContactMechanismTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactMechanismTypeServiceImpl extends AbstractPersistentEnumServiceImpl<ContactMechanismType> implements ContactMechanismTypeService {
    @Autowired
    private ContactMechanismTypeDao dao;

    @Override
    protected AbstractPersistentEnumDao<ContactMechanismType> getDao() {
        return dao;
    }
}
