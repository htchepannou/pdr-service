package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.dto.party.CreatePartyElectronicAddressRequest;

public interface PartyElectronicAddressService extends AbstractPartyContactMechanismService<PartyElectronicAddress>{
    PartyElectronicAddress addEmail (Party party, String email);

    PartyElectronicAddress addAddress(Party party, CreatePartyElectronicAddressRequest request);

    PartyElectronicAddress updateAddress(Party party, long addressId, CreatePartyElectronicAddressRequest request);
}
