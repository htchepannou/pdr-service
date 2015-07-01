package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyElectronicAddressService;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PartyElectronicAddressServiceImpl implements PartyElectronicAddressService {
    @Autowired
    private PartyElectronicAddressDao dao;

    @Override
    public PartyElectronicAddress findById(final long id) {
        PartyElectronicAddress address = dao.findById(id);
        if (address == null){
            throw new NotFoundException(id);
        }
        return address;
    }

    @Override
    public List<PartyElectronicAddress> findByParty(final long partyId) {
        return dao.findByParty(partyId);
    }

    @Override
    public void create(final PartyElectronicAddress partyElectronicAddress) {
        long id = dao.create(partyElectronicAddress);
        partyElectronicAddress.setId(id);
    }

    @Override
    public void update(final PartyElectronicAddress partyElectronicAddress) {
        dao.update(partyElectronicAddress);
    }

    @Override
    public void delete(long id) {
        dao.findByParty(id);
    }
}
