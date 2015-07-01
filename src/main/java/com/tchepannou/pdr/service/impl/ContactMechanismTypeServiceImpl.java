package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.ContactMechanismTypeDao;
import com.tchepannou.pdr.domain.ContactMechanismType;
import com.tchepannou.pdr.service.ContactMechanismTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactMechanismTypeServiceImpl implements ContactMechanismTypeService {
    @Autowired
    private ContactMechanismTypeDao dao;

    @Override
    public ContactMechanismType findById(final long id) {
        return dao.findById(id);
    }

    @Override
    public ContactMechanismType findByName(final String name) {
        return dao.findByName(name);
    }

    @Override
    public List<ContactMechanismType> findAll() {
        return dao.findAll();
    }
}
