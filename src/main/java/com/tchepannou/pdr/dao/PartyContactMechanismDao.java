package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.PartyContactMecanism;

import java.util.List;

public interface PartyContactMechanismDao<T extends PartyContactMecanism> {
    T findById(long id);

    List<T> findByParty(long partyId);

    long create(T partyContactMechanism);

    void update(T partyContactMechanism);

    void delete(long id);
}
