package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.PartyElectronicAddress;

import java.util.List;

public interface PartyElectronicAddressService {
    List<PartyElectronicAddress> findByParty (long partyId);
}
