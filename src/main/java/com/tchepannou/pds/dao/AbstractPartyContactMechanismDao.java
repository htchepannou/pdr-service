package com.tchepannou.pds.dao;

import com.tchepannou.pds.domain.PartyContactMecanism;

import java.util.List;

public interface AbstractPartyContactMechanismDao<T extends PartyContactMecanism> {
    T findById(long id);

    List<T> findByParty(long partyId);

    long create(T partyContactMechanism);

    void update(T partyContactMechanism);

    void delete(long id);
}
