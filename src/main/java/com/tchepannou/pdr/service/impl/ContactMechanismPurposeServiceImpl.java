package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.ContactMechanismPurposeDao;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactMechanismPurposeServiceImpl implements ContactMechanismPurposeService {
    @Autowired
    private ContactMechanismPurposeDao dao;

    @Override
    public ContactMechanismPurpose findById(final long id) {
        ContactMechanismPurpose purpose = dao.findById(id);
        if (purpose == null){
            throw new NotFoundException(id);
        }
        return purpose;
    }

    @Override
    public ContactMechanismPurpose findByName(final String name) {
        ContactMechanismPurpose purpose = name != null ? dao.findByName(name) : null;
        if (purpose == null){
            throw new NotFoundException(name);
        }
        return purpose;
    }

    @Override
    public List<ContactMechanismPurpose> findAll() {
        return dao.findAll();
    }
}
