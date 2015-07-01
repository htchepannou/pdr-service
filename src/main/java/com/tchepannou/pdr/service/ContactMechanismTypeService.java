package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.ContactMechanismType;

import java.util.List;

public interface ContactMechanismTypeService {
    ContactMechanismType findById(long id);

    List<ContactMechanismType> findAll();
}
