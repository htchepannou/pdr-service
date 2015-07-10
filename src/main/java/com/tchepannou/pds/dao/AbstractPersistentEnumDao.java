package com.tchepannou.pds.dao;

import com.tchepannou.pds.domain.PersistentEnum;

import java.util.List;

public interface AbstractPersistentEnumDao<T extends PersistentEnum> {
    T findById(long id);

    T findByName(String name);

    List<T> findAll();
}
