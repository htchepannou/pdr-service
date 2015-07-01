package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.CreatePartyElectronicAddressResquest;
import com.tchepannou.pdr.dto.party.PartyElectronicAddressResponse;
import com.tchepannou.pdr.dto.party.PartyElectronicAddressResquest;
import com.tchepannou.pdr.dto.party.PartyResponse;
import com.tchepannou.pdr.exception.BadRequestException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if (party == null || party.isDeleted()) {
            throw new NotFoundException(partyId);
        }

        PartyResponse.Builder builder = new PartyResponse
                .Builder()
                .withParty(party);

        withElectronicAddresses(party, builder);

        return builder.build();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/e-address")
    @ApiOperation("Add an electronic address to a party")
    public PartyElectronicAddressResponse addElectronicAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyElectronicAddressResquest request
    ) {
        final ElectronicAddress electronicAddress = findElectronicAddress(request.getAddress());

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = contactMechanismTypeService.findByName(request.getType());

        final PartyElectronicAddress partyElectronicAddress = new PartyElectronicAddress();
        partyElectronicAddress.setPartyId(partyId);
        partyElectronicAddress.setTypeId(type.getId());
        update(request, purpose, partyElectronicAddress);
        partyElectronicAddressService.create(partyElectronicAddress);

        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/e-address/{eaddressId}")
    @ApiOperation("Update an electronic address")
    public PartyElectronicAddressResponse updateElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId,
            @Valid @RequestBody PartyElectronicAddressResquest request
    ) {
        final PartyElectronicAddress partyElectronicAddress = partyElectronicAddressService.findById(eaddressId);
        if (partyElectronicAddress.getPartyId() != partyId) {
            throw new BadRequestException();
        }

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ElectronicAddress electronicAddress = findElectronicAddress(request.getAddress());

        update(request, purpose, partyElectronicAddress);
        partyElectronicAddressService.update(partyElectronicAddress);

        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/e-address/{eaddressId}")
    @ApiOperation("Delete an electronic address")
    public void deleteElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId
    ) {
        PartyElectronicAddress eaddress = partyElectronicAddressService.findById(eaddressId);
        if (eaddress.getPartyId() != partyId) {
            throw new BadRequestException();
        }
        partyElectronicAddressService.delete(eaddressId);
    }



    //-- Private
    private ContactMechanismPurpose findPurpose (String name){
        if (name != null) {
            try {
                return contactMechanismPurposeService.findByName(name);
            } catch (NotFoundException e) { // NOSONAR
            }
        }
        return null;
    }

    private ElectronicAddress findElectronicAddress (String address) {
        String hash = ElectronicAddress.hash(address);
        ElectronicAddress electronicAddress;
        try {
            electronicAddress = electronicAddressService.findByHash(hash);
        } catch (NotFoundException e) {
            electronicAddress = new ElectronicAddress();
            electronicAddress.setAddress(address);
            electronicAddressService.create(electronicAddress);
        }
        return electronicAddress;
    }

    private void update (
            final PartyElectronicAddressResquest request,
            final ContactMechanismPurpose purpose,
            final PartyElectronicAddress partyElectronicAddress
    ){
        partyElectronicAddress.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyElectronicAddress.setNoSolicitation(request.isNoSolicitation());
        partyElectronicAddress.setPurposeId(purpose != null ? 0 : purpose.getId());
        partyElectronicAddressService.update(partyElectronicAddress);

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
