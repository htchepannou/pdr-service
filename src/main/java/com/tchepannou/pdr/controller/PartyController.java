package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.dto.party.PartyResponse;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(basePath = "/parties", value = "People, Organization and Household", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyController {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    @Autowired
    private ContactMechanismPurposeService contactMechanismPurposeService;

    @Autowired
    private ContactMechanismTypeService contactMechanismTypeService;

    @Autowired
    private ElectronicAddressService electronicAddressService;

    @Autowired
    private PartyElectronicAddressService partyElectronicAddressService;


    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}")
    @ApiOperation("Returns a Party")
    public PartyResponse findById(@PathVariable final long partyId) {
        final Party party = partyService.findById(partyId);
        if (party == null) {
            throw new NotFoundException(partyId);
        }

        PartyResponse.Builder builder = new PartyResponse
                .Builder()
                .withParty(party);

        withElectronicAddresses(party, builder);

        return builder.build();
    }

    private void withElectronicAddresses (Party party, PartyResponse.Builder builder) {
        final List<PartyElectronicAddress> partyElectronicAddresses = partyElectronicAddressService.findByParty(party.getId());

        final List<Long> addressIds = partyElectronicAddresses.stream()
                .map(PartyElectronicAddress::getContactId)
                .collect(Collectors.toList());

        final List<ElectronicAddress> electronicAddresses = electronicAddressService.findByIds(addressIds);

        builder.withContactMechanismPurposeService(contactMechanismPurposeService)
                .withContactMechanismTypeService(contactMechanismTypeService)
                .withElectronicAddresses(electronicAddresses)
                .withPartyElectronicAddresses(partyElectronicAddresses);

    }
}
