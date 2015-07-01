package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.PartyElectronicAddress;

import java.util.List;

public interface PartyElectronicAddressService {
    PartyElectronicAddress findById(long id);

    List<PartyElectronicAddress> findByParty (long partyId);

    void create (PartyElectronicAddress partyElectronicAddress);

    void update (PartyElectronicAddress partyElectronicAddress);

    void delete(long id);

}
