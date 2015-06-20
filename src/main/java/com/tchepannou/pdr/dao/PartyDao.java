package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.Party;

public interface PartyDao {
    Party findById (long id);
}
