package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractContactMechanismDao;
import com.tchepannou.pdr.domain.ContactMechanism;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AbstractContactMechanismService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public abstract class AbstractContactMecanismServiceImpl<T extends ContactMechanism> implements AbstractContactMechanismService<T> {
    //-- Abstract
    protected abstract AbstractContactMechanismDao<T> getDao ();

    //-- AbstractContactMechanismService overrides
    @Override
    public T findByHash (String hash) {
        T out = getDao().findByHash(hash);
        if (out == null){
            throw new NotFoundException(hash);
        }
        return out;
    }

    @Override
    public List<T> findByIds(Collection<? extends Long> ids) {
        return getDao().findByIds(ids);
    }

    @Override
    @Transactional
    public void create(T address) {
        long id = getDao().create(address);
        address.setId(id);
    }
}
