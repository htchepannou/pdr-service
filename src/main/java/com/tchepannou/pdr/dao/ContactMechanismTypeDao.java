package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ContactMechanismType;

import java.util.List;

public interface ContactMechanismTypeDao {
    ContactMechanismType findById(long id);

    ContactMechanismType findByName(String name);

    List<ContactMechanismType> findAll();
}
