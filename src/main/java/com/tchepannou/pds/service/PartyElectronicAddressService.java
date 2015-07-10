package com.tchepannou.pds.service;

import com.tchepannou.pds.domain.PartyElectronicAddress;
import com.tchepannou.pds.dto.party.CreatePartyElectronicAddressRequest;
import com.tchepannou.pds.dto.party.PartyElectronicAddressRequest;
import com.tchepannou.pds.dto.party.PartyElectronicAddressResponse;

public interface PartyElectronicAddressService extends AbstractPartyContactMechanismService<PartyElectronicAddress>{
    PartyElectronicAddressResponse addEmail (long partyId, String email);

    PartyElectronicAddressResponse addAddress(long partyId, CreatePartyElectronicAddressRequest request);

    PartyElectronicAddressResponse updateAddress(long partyId, long addressId, PartyElectronicAddressRequest request);

    void removeAddress(long partyId, long addressId);
}
