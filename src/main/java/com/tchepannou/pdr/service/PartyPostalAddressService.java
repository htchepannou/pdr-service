package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyPostalAddress;
import com.tchepannou.pdr.dto.party.CreatePartyPostalAddressRequest;
import com.tchepannou.pdr.dto.party.PartyPostalAddressRequest;

public interface PartyPostalAddressService extends AbstractPartyContactMechanismService<PartyPostalAddress>{
    PartyPostalAddress addAddress(Party party, CreatePartyPostalAddressRequest request);

    PartyPostalAddress updateAddress(Party party, long addressId, PartyPostalAddressRequest request);

    void removeAddress(Party party, long addressId);
}
