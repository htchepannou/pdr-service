package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.ContactMechanismPurposeDao;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.service.ContactMechanismPurposeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactMechanismPurposeServiceImpl implements ContactMechanismPurposeService {
    @Autowired
    private ContactMechanismPurposeDao dao;

    @Override
    public ContactMechanismPurpose findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<ContactMechanismPurpose> findAll() {
        return dao.findAll();
    }
}
