package com.tchepannou.pdr.service;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyPhone;
import com.tchepannou.pdr.dto.party.CreatePartyPhoneRequest;
import com.tchepannou.pdr.dto.party.PartyPhoneRequest;

public interface PartyPhoneService extends AbstractPartyContactMechanismService<PartyPhone>{
    PartyPhone addAddress(Party party, CreatePartyPhoneRequest request);

    PartyPhone updateAddress(Party party, long addressId, PartyPhoneRequest request);

    void removeAddress(Party party, long addressId);
}
