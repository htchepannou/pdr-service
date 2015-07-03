package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Party;

public interface PartyDao {
    Party findById (long id);

    Party findByUser (long userId);

    long create (Party party);

    void update (Party party);
}
