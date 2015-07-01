package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ContactMechanismPurpose;

import java.util.List;

public interface ContactMechanismPurposeDao {
    ContactMechanismPurpose findById(long id);

    List<ContactMechanismPurpose> findAll();
}
