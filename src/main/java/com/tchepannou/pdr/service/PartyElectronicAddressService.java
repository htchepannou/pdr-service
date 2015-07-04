package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.dto.party.CreatePartyElectronicAddressRequest;
import com.tchepannou.pdr.dto.party.PartyElectronicAddressRequest;

public interface PartyElectronicAddressService extends AbstractPartyContactMechanismService<PartyElectronicAddress>{
    PartyElectronicAddress addEmail (Party party, String email);

    PartyElectronicAddress addAddress(Party party, CreatePartyElectronicAddressRequest request);

    PartyElectronicAddress updateAddress(Party party, long addressId, PartyElectronicAddressRequest request);
}
