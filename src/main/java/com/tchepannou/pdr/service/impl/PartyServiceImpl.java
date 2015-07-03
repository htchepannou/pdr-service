package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.dto.party.CreatePartyRequest;
import com.tchepannou.pdr.enums.PartyKind;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class PartyServiceImpl implements PartyService {
    //-- Attributes
    @Autowired
    private PartyDao partyDao;

    //-- PartyService overrides
    @Override
    public Party findById(long id) {
        Party party = partyDao.findById(id);
        if (party == null) {
            throw new NotFoundException(id);
        }
        return party;
    }

    @Override
    public Party create(final CreatePartyRequest request) {
        Party party = new Party();
        party.setKind(PartyKind.fromText(request.getKind()));
        party.setFirstName(request.getFirstName());
        party.setLastName(request.getLastName());
        party.setName(request.getName());
        party.setFromDate(new Date());
        partyDao.create(party);

        return party;
    }

    @Override
    public void update(Party party) {
        partyDao.update(party);
    }
}
