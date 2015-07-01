package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PartyElectronicAddressServiceImpl implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Override
    public List<PartyElectronicAddress> findByParty(long partyId) {
        return dao.findByParty(partyId);
    }
}
