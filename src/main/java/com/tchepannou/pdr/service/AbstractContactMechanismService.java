package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.ContactMechanism;

import java.util.Collection;
import java.util.List;

public interface AbstractContactMechanismService<T extends ContactMechanism> {
    T findById(long id);

    T findByHash(String hash);

    List<T> findByIds(Collection<? extends Long> ids);

    void create(T address);
}
