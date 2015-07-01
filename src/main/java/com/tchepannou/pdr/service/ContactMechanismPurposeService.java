package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.ContactMechanismPurpose;

import java.util.List;

public interface ContactMechanismPurposeService {
    ContactMechanismPurpose findById(long id);

    ContactMechanismPurpose findByName(String name);

    List<ContactMechanismPurpose> findAll();
}
