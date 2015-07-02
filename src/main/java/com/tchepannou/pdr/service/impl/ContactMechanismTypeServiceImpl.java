package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.dao.ContactMechanismTypeDao;
import com.tchepannou.pdr.domain.ContactMechanismType;
import com.tchepannou.pdr.service.ContactMechanismTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactMechanismTypeServiceImpl extends AbstractPersistentEnumServiceImpl<ContactMechanismType> implements ContactMechanismTypeService {
    @Autowired
    private ContactMechanismTypeDao dao;

    @Override
    protected AbstractPersistentEnumDao<ContactMechanismType> getDao() {
        return dao;
    }
}
