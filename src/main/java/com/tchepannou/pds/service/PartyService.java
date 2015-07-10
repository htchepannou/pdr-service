package com.tchepannou.pds.service;

import com.tchepannou.pds.dto.party.CreatePartyRequest;
import com.tchepannou.pds.dto.party.PartyResponse;

public interface PartyService {
    PartyResponse findById(long id);

    PartyResponse create (CreatePartyRequest request);
}
