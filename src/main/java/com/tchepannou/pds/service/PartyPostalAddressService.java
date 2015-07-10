package com.tchepannou.pds.service;

import com.tchepannou.pds.domain.PartyPostalAddress;
import com.tchepannou.pds.dto.party.CreatePartyPostalAddressRequest;
import com.tchepannou.pds.dto.party.PartyPostalAddressRequest;
import com.tchepannou.pds.dto.party.PartyPostalAddressResponse;

public interface PartyPostalAddressService extends AbstractPartyContactMechanismService<PartyPostalAddress>{
    PartyPostalAddressResponse addAddress(long partyId, CreatePartyPostalAddressRequest request);

    PartyPostalAddressResponse updateAddress(long partyId, long addressId, PartyPostalAddressRequest request);

    void removeAddress(long partyId, long addressId);
}
