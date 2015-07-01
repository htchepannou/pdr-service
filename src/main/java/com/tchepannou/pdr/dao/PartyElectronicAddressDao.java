package com.tchepannou.pdr.dao;

import com.tchepannou.pdr.domain.PartyElectronicAddress;

import java.util.List;

public interface PartyElectronicAddressDao {
    List<PartyElectronicAddress> findByParty (long partyId);
}
