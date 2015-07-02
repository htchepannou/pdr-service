package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPersistentEnumDao;
import com.tchepannou.pdr.domain.PersistentEnum;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AbstractPersistentEnumService;

import java.util.List;

public abstract class AbstractPersistentEnumServiceImpl<T extends PersistentEnum> implements AbstractPersistentEnumService<T> {
    //-- Abstract methods
    protected abstract AbstractPersistentEnumDao<T> getDao ();

    //-- AbstractPersistentEnumService overrides
    @Override
    public T findById(final long id) {
        T purpose = getDao().findById(id);
        if (purpose == null){
            throw new NotFoundException(id);
        }
        return purpose;
    }

    @Override
    public T findByName(final String name) {
        T purpose = name != null ? getDao().findByName(name) : null;
        if (purpose == null){
            throw new NotFoundException(name);
        }
        return purpose;
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }
}
