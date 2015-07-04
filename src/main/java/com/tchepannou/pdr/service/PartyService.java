package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.dto.party.CreatePartyRequest;

public interface PartyService {
    Party findById(long id);

    Party create (CreatePartyRequest request);
}
