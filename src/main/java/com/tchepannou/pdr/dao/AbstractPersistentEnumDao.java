package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.PersistentEnum;

import java.util.List;

public interface AbstractPersistentEnumDao<T extends PersistentEnum> {
    T findById(long id);

    T findByName(String name);

    List<T> findAll();
}
