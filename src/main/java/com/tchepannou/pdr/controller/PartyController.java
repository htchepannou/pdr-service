package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.*;
import com.tchepannou.pdr.enums.Privacy;
import com.tchepannou.pdr.exception.BadRequestException;
import com.tchepannou.pdr.exception.NotFoundException;
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
        PartyElectronicAddress eaddress = partyElectronicAddressService.findById(eaddressId);
        if (eaddress.getPartyId() != partyId) {
            throw new NotFoundException();
        }
        partyElectronicAddressService.delete(eaddressId);
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
        PartyPostalAddress paddress = partyPostalAddressService.findById(paddressId);
        if (paddress.getPartyId() != partyId) {
            throw new NotFoundException();
        }
        partyPostalAddressService.delete(paddressId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/phones")
    @ApiOperation("Add an postal address to a party")
    public PartyPhoneResponse addPhone (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPhoneRequest request
    ) {
        final Phone postalAddress = findPhone(request);

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = contactMechanismTypeService.findByName(request.getType());

        final PartyPhone partyPhone = new PartyPhone();
        partyPhone.setPartyId(partyId);
        partyPhone.setTypeId(type.getId());
        update(request, purpose, partyPhone, partyPhoneService);
        partyPhoneService.create(partyPhone);

        return new PartyPhoneResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPhone(postalAddress)
                .withPartyPhone(partyPhone)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Update an postal address")
    public PartyPhoneResponse updatePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId,
            @Valid @RequestBody PartyPhoneRequest request
    ) {
        final PartyPhone partyPhone = partyPhoneService.findById(phoneId);
        if (partyPhone.getPartyId() != partyId) {
            throw new BadRequestException();
        }

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final Phone postalAddress = findPhone(request);

        update(request, purpose, partyPhone, partyPhoneService);
        partyPhoneService.update(partyPhone);

        return new PartyPhoneResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPhone(postalAddress)
                .withPartyPhone(partyPhone)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Delete an postal address")
    public void deletePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId
    ) {
        PartyPhone phone = partyPhoneService.findById(phoneId);
        if (phone.getPartyId() != partyId) {
            throw new BadRequestException();
        }
        partyPhoneService.delete(phoneId);
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


    private ContactMechanismPurpose findPurpose (String name){
        if (name != null) {
            try {
                return contactMechanismPurposeService.findByName(name);
            } catch (NotFoundException e) { // NOSONAR
            }
        }
        return null;
    }

    private Phone findPhone (final PartyPhoneRequest request) {
        final String hash = Phone.computeHash(
                request.getCountryCode(),
                request.getNumber(),
                request.getExtension()
        );
        Phone phone;
        try {
            phone = phoneService.findByHash(hash);
        } catch (NotFoundException e) {     // NOSONAR
            phone = new Phone();
            phone.setCountryCode(request.getCountryCode());
            phone.setNumber(request.getNumber());
            phone.setExtension(request.getExtension());
            phoneService.create(phone);
        }
        return phone;
    }

    private void update (
            final AbstractPartyContactMechanismRequest request,
            final ContactMechanismPurpose purpose,
            final PartyContactMecanism partyPostalAddress,
            final AbstractPartyContactMechanismService service
    ){
        partyPostalAddress.setPrivacy(Privacy.fromText(request.getPrivacy()));
        partyPostalAddress.setNoSolicitation(request.isNoSolicitation());
        partyPostalAddress.setPurposeId(purpose == null ? 0 : purpose.getId());
        service.update(partyPostalAddress);
    }    
}
