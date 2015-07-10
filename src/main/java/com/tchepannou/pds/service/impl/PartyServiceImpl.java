package com.tchepannou.pds.service.impl;

import com.tchepannou.pds.dao.PartyDao;
import com.tchepannou.pds.domain.Party;
import com.tchepannou.pds.dto.party.CreatePartyRequest;
import com.tchepannou.pds.dto.party.PartyResponse;
import com.tchepannou.pds.enums.PartyKind;
import com.tchepannou.pds.exception.NotFoundException;
import com.tchepannou.pds.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PartyServiceImpl implements PartyService {
    //-- Attributes
    @Autowired
    private PartyDao partyDao;

    //-- PartyService overrides
    @Override
    public PartyResponse findById(long id) {
        Party party = partyDao.findById(id);
        if (party == null) {
            throw new NotFoundException(id, Party.class);
        }
        return toPartyResponse(party);
    }

    @Override
    public PartyResponse create(final CreatePartyRequest request) {
        Party party = new Party();
        party.setKind(PartyKind.fromText(request.getKind()));
        party.setFirstName(request.getFirstName());
        party.setLastName(request.getLastName());
        party.setName(request.getName());
        party.setFromDate(new Date());
        partyDao.create(party);

        return toPartyResponse(party);
    }

    //-- Private
    private PartyResponse toPartyResponse (final Party party) {
        return new PartyResponse
                .Builder()
                .withParty(party)
                .build();
    }
}
