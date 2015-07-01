package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.PartyElectronicAddress;

import java.util.List;

public interface PartyElectronicAddressDao {
    PartyElectronicAddress findById (long id);

    List<PartyElectronicAddress> findByParty (long partyId);

    long create (PartyElectronicAddress partyElectronicAddress);

    void update (PartyElectronicAddress partyElectronicAddress);

    void delete(long id);
}
