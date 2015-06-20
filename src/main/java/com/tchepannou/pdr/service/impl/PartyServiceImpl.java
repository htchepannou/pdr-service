package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;

public class PartyServiceImpl implements PartyService {
    //-- Attributes
    @Autowired
    private PartyDao partyDao;

    //-- PartyService overrides
    @Override
    public Party findById(long id) {
        return partyDao.findById(id);
    }
}
