package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.ContactMechanism;

import java.util.Collection;
import java.util.List;

public interface AbstractContactMechanismDao<T extends ContactMechanism> {
    T findByHash(String hash);

    List<T> findByIds(Collection<? extends Long> ids);

    long create(T address);
}
