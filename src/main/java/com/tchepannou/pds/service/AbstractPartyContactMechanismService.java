package com.tchepannou.pds.service;

import com.tchepannou.pds.domain.PartyContactMecanism;

import java.util.List;

public interface AbstractPartyContactMechanismService<T extends PartyContactMecanism> {
    T findById(long id);

    List<T> findByParty(long partyId);
}
