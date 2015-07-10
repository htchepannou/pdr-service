package com.tchepannou.pds.service;

import com.tchepannou.pds.domain.PersistentEnum;

import java.util.List;

public interface AbstractPersistentEnumService<T extends PersistentEnum> {
    T findById(long id);

    T findByName(String name);

    List<T> findAll();
}
