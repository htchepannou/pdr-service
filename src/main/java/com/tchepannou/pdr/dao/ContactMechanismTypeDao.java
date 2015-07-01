package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ContactMechanismType;

import java.util.List;

public interface ContactMechanismTypeDao {
    ContactMechanismType findById(long id);

    List<ContactMechanismType> findAll();
}
