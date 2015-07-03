package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyElectronicAddress;

public interface PartyElectronicAddressService extends AbstractPartyContactMechanismService<PartyElectronicAddress>{
    PartyElectronicAddress addEmail (Party party, String email);
}
