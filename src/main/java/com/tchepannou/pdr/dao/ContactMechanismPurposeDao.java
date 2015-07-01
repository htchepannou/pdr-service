package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ContactMechanismPurpose;

import java.util.List;

public interface ContactMechanismPurposeDao {
    ContactMechanismPurpose findById(long id);

    ContactMechanismPurpose findByName(String name);

    List<ContactMechanismPurpose> findAll();
}
