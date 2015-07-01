package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao;
import com.tchepannou.pdr.domain.PartyContactMecanism;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AbstractPartyContactMechanismService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractPartyContactMechanismServiceImpl<T extends PartyContactMecanism>  implements AbstractPartyContactMechanismService<T> {
    //-- Abstract
    protected abstract AbstractPartyContactMechanismDao<T> getDao();

    //-- AbstractPartyContactMechanismService overrides
    @Override
    public T findById(final long id) {
        T address = getDao().findById(id);
        if (address == null){
            throw new NotFoundException(id);
        }
        return address;
    }

    @Override
    public List<T> findByParty(final long partyId) {
        return getDao().findByParty(partyId);
    }

    @Override
    @Transactional
    public void create(final T partyContactMechanism) {
        long id = getDao().create(partyContactMechanism);
        partyContactMechanism.setId(id);
    }

    @Override
    @Transactional
    public void update(final T partyContactMechanism) {
        getDao().update(partyContactMechanism);
    }

    @Override
    @Transactional
    public void delete(long id) {
        getDao().delete(id);
    }
}