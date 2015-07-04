package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.*;
import com.tchepannou.pdr.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(basePath = "/parties", value = "People, Organization and Household", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyController extends AbstractController{
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger(PartyController.class);

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

    @Autowired
    private PostalAddressService postalAddressService;

    @Autowired
    private PartyPostalAddressService partyPostalAddressService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PartyPhoneService partyPhoneService;


    //-- AbstractController overrides
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}")
    @ApiOperation("Returns a Party ")
    public PartyResponse findById(@PathVariable final long partyId) {
        final Party party = partyService.findById(partyId);

        return new PartyResponse
                .Builder()
                .withParty(party)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}/contacts")
    @ApiOperation("Returns a Party contacts mechanisms")
    public ContactMechanismListResponse contacts(@PathVariable final long partyId) {
        final Party party = partyService.findById(partyId);

        /* electronic addresses */
        final List<PartyElectronicAddress> partyElectronicAddresses = partyElectronicAddressService.findByParty(party.getId());

        final List<Long> electronicAddressIds = partyElectronicAddresses.stream()
                .map(PartyElectronicAddress::getContactId)
                .collect(Collectors.toList());

        final List<ElectronicAddress> electronicAddresses = electronicAddressService.findByIds(electronicAddressIds);
        
        /* postal addresses */
        final List<PartyPostalAddress> partyPostalAddresses = partyPostalAddressService.findByParty(party.getId());

        final List<Long> postalAddressIds = partyPostalAddresses.stream()
                .map(PartyPostalAddress::getContactId)
                .collect(Collectors.toList());

        final List<PostalAddress> postalAddresses = postalAddressService.findByIds(postalAddressIds);
        
        /* phones */
        final List<PartyPhone> partyPhones = partyPhoneService.findByParty(party.getId());

        final List<Long> phoneIds = partyPhones.stream()
                .map(PartyPhone::getContactId)
                .collect(Collectors.toList());

        final List<Phone> phones = phoneService.findByIds(phoneIds);
        
        return new ContactMechanismListResponse
                .Builder()
                .withContactMechanismPurposeService(contactMechanismPurposeService)
                .withContactMechanismTypeService(contactMechanismTypeService)
                .withElectronicAddresses(electronicAddresses)
                .withPartyElectronicAddresses(partyElectronicAddresses)
                .withPostalAddresses(postalAddresses)
                .withPartyPostalAddresses(partyPostalAddresses)
                .withPhones(phones)
                .withPartyPhones(partyPhones)
                .build();
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/e-addresses")
    @ApiOperation("Add an electronic address to a party")
    public PartyElectronicAddressResponse addElectronicAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyElectronicAddressRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyElectronicAddress partyElectronicAddress = partyElectronicAddressService.addAddress(party, request);

        return toPartyElectronicAddressResponse(partyElectronicAddress);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
    @ApiOperation("Update an electronic address")
    public PartyElectronicAddressResponse updateElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId,
            @Valid @RequestBody PartyElectronicAddressRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyElectronicAddress partyElectronicAddress = partyElectronicAddressService.updateAddress(party, eaddressId, request);

        return toPartyElectronicAddressResponse(partyElectronicAddress);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
    @ApiOperation("Delete an electronic address")
    public void deleteElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId
    ) {
        final Party party = partyService.findById(partyId);
        partyElectronicAddressService.removeAddress(party, eaddressId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/p-addresses")
    @ApiOperation("Add an postal address to a party")
    public PartyPostalAddressResponse addPostalAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPostalAddressRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyPostalAddress partyPostalAddress = partyPostalAddressService.addAddress(party, request);

        return toPartyPostalAddressResponse(partyPostalAddress);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Update an postal address")
    public PartyPostalAddressResponse updatePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId,
            @Valid @RequestBody PartyPostalAddressRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyPostalAddress partyPostalAddress = partyPostalAddressService.updateAddress(party, paddressId, request);

        return toPartyPostalAddressResponse(partyPostalAddress);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Delete an postal address")
    public void deletePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId
    ) {
        final Party party = partyService.findById(partyId);
        partyPostalAddressService.removeAddress(party, paddressId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/phones")
    @ApiOperation("Add an postal address to a party")
    public PartyPhoneResponse addPhone (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPhoneRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyPhone partyPhone = partyPhoneService.addAddress(party, request);

        return toPartyPhoneResponse(partyPhone);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Update an postal address")
    public PartyPhoneResponse updatePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId,
            @Valid @RequestBody PartyPhoneRequest request
    ) {
        final Party party = partyService.findById(partyId);

        final PartyPhone partyPhone = partyPhoneService.updateAddress(party, phoneId, request);

        return toPartyPhoneResponse(partyPhone);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Delete an postal address")
    public void deletePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId
    ) {
        final Party party = partyService.findById(partyId);
        partyPhoneService.removeAddress(party, phoneId);
    }

    //-- Private
    private PartyElectronicAddressResponse toPartyElectronicAddressResponse (final PartyElectronicAddress partyElectronicAddress) {
        final ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(partyElectronicAddress.getPurposeId());

        final ElectronicAddress electronicAddress = electronicAddressService.findById(partyElectronicAddress.getContactId());

        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }

    private PartyPostalAddressResponse toPartyPostalAddressResponse (final PartyPostalAddress partyPostalAddress) {
        final ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(partyPostalAddress.getPurposeId());

        final PostalAddress postalAddress = postalAddressService.findById(partyPostalAddress.getContactId());

        return new PartyPostalAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPostalAddress(postalAddress)
                .withPartyPostalAddress(partyPostalAddress)
                .build();
    }

    private PartyPhoneResponse toPartyPhoneResponse (final PartyPhone partyPhone) {
        final ContactMechanismPurpose purpose = contactMechanismPurposeService.findById(partyPhone.getPurposeId());

        final Phone phone = phoneService.findById(partyPhone.getContactId());

        return new PartyPhoneResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPhone(phone)
                .withPartyPhone(partyPhone)
                .build();
    }
}
