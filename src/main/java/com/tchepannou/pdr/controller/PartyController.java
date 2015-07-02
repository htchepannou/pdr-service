package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.dto.party.*;
import com.tchepannou.pdr.enums.Privacy;
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

    @Autowired
    private PostalAddressService postalAddressService;

    @Autowired
    private PartyPostalAddressService partyPostalAddressService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PartyPhoneService partyPhoneService;


    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}")
    @ApiOperation("Returns a Party ")
    public PartyResponse findById(@PathVariable final long partyId) {
        final Party party = partyService.findById(partyId);
        if (party == null || party.isDeleted()) {
            throw new NotFoundException(partyId);
        }

        return new PartyResponse
                .Builder()
                .withParty(party)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}/contacts")
    @ApiOperation("Returns a Party contacts mechanisms")
    public ContactMechanismListResponse contacts(@PathVariable final long partyId) {
        final Party party = partyService.findById(partyId);
        if (party == null || party.isDeleted()) {
            throw new NotFoundException(partyId);
        }

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
        final ElectronicAddress electronicAddress = findElectronicAddress(request);

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = contactMechanismTypeService.findByName(request.getType());

        final PartyElectronicAddress partyElectronicAddress = new PartyElectronicAddress();
        partyElectronicAddress.setPartyId(partyId);
        partyElectronicAddress.setTypeId(type.getId());
        update(request, purpose, partyElectronicAddress, partyElectronicAddressService);
        partyElectronicAddressService.create(partyElectronicAddress);

        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
    @ApiOperation("Update an electronic address")
    public PartyElectronicAddressResponse updateElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId,
            @Valid @RequestBody PartyElectronicAddressRequest request
    ) {
        final PartyElectronicAddress partyElectronicAddress = partyElectronicAddressService.findById(eaddressId);
        if (partyElectronicAddress.getPartyId() != partyId) {
            throw new BadRequestException();
        }

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ElectronicAddress electronicAddress = findElectronicAddress(request);

        update(request, purpose, partyElectronicAddress, partyElectronicAddressService);
        partyElectronicAddressService.update(partyElectronicAddress);

        return new PartyElectronicAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withElectronicAddress(electronicAddress)
                .withPartyElectronicAddress(partyElectronicAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
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

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/p-addresses")
    @ApiOperation("Add an postal address to a party")
    public PartyPostalAddressResponse addPostalAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPostalAddressRequest request
    ) {
        final PostalAddress postalAddress = findPostalAddress(request);

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final ContactMechanismType type = contactMechanismTypeService.findByName(request.getType());

        final PartyPostalAddress partyPostalAddress = new PartyPostalAddress();
        partyPostalAddress.setPartyId(partyId);
        partyPostalAddress.setTypeId(type.getId());
        update(request, purpose, partyPostalAddress, partyPostalAddressService);
        partyPostalAddressService.create(partyPostalAddress);

        return new PartyPostalAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPostalAddress(postalAddress)
                .withPartyPostalAddress(partyPostalAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Update an postal address")
    public PartyPostalAddressResponse updatePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId,
            @Valid @RequestBody PartyPostalAddressRequest request
    ) {
        final PartyPostalAddress partyPostalAddress = partyPostalAddressService.findById(paddressId);
        if (partyPostalAddress.getPartyId() != partyId) {
            throw new BadRequestException();
        }

        final ContactMechanismPurpose purpose = findPurpose(request.getPurpose());

        final PostalAddress postalAddress = findPostalAddress(request);

        update(request, purpose, partyPostalAddress, partyPostalAddressService);
        partyPostalAddressService.update(partyPostalAddress);

        return new PartyPostalAddressResponse.Builder()
                .withContactMechanismPurpose(purpose)
                .withPostalAddress(postalAddress)
                .withPartyPostalAddress(partyPostalAddress)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Delete an postal address")
    public void deletePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId
    ) {
        PartyPostalAddress paddress = partyPostalAddressService.findById(paddressId);
        if (paddress.getPartyId() != partyId) {
            throw new BadRequestException();
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
    private ContactMechanismPurpose findPurpose (String name){
        if (name != null) {
            try {
                return contactMechanismPurposeService.findByName(name);
            } catch (NotFoundException e) { // NOSONAR
            }
        }
        return null;
    }

    private ElectronicAddress findElectronicAddress (final PartyElectronicAddressRequest request) {
        final String address = request.getAddress();
        final String hash = ElectronicAddress.computeHash(address);
        ElectronicAddress electronicAddress;
        try {
            electronicAddress = electronicAddressService.findByHash(hash);
        } catch (NotFoundException e) { // NOSONAR
            electronicAddress = new ElectronicAddress();
            electronicAddress.setAddress(address);
            electronicAddressService.create(electronicAddress);
        }
        return electronicAddress;
    }

    private PostalAddress findPostalAddress (final PartyPostalAddressRequest request) {
        final String hash = PostalAddress.computeHash(
                request.getStreet1(),
                request.getStreet2(),
                request.getCity(),
                request.getStateCode(),
                request.getZipCode(),
                request.getCountryCode()
        );
        PostalAddress postalAddress;
        try {
            postalAddress = postalAddressService.findByHash(hash);
        } catch (NotFoundException e) {     // NOSONAR
            postalAddress = new PostalAddress();
            postalAddress.setStreet1(request.getStreet1());
            postalAddress.setStreet2(request.getStreet2());
            postalAddress.setCity(request.getCity());
            postalAddress.setStateCode(request.getStateCode());
            postalAddress.setZipCode(request.getZipCode());
            postalAddress.setCountryCode(request.getCountryCode());
            postalAddressService.create(postalAddress);
        }
        return postalAddress;
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
