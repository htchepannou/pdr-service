package com.tchepannou.pds.dao;

import com.tchepannou.pds.domain.ContactMechanism;

import java.util.Collection;
import java.util.List;

public interface AbstractContactMechanismDao<T extends ContactMechanism> {
    T findById(long id);

    T findByHash(String hash);

    List<T> findByIds(Collection<? extends Long> ids);

    long create(T address);
}
