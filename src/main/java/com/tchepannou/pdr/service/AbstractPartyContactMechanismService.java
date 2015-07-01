package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.PartyContactMecanism;

import java.util.List;

public interface AbstractPartyContactMechanismService<T extends PartyContactMecanism> {
    T findById(long id);

    List<T> findByParty(long partyId);

    void create(T partyElectronicAddress);

    void update(T partyElectronicAddress);

    void delete(long id);

}
