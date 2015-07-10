package com.tchepannou.pds.controller;

import com.tchepannou.pds.dto.ErrorResponse;
import com.tchepannou.pds.dto.party.*;
import com.tchepannou.pds.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(basePath = "/parties", value = "People, Organization and Household", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyController extends AbstractController{
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger(PartyController.class);

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyContactMechanismService partyContactMechanismService;

    @Autowired
    private PartyElectronicAddressService partyElectronicAddressService;

    @Autowired
    private PartyPostalAddressService partyPostalAddressService;

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
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public PartyResponse findById(@PathVariable final long partyId) {
        return partyService.findById(partyId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{partyId}/contacts")
    @ApiOperation("Returns a Party contacts mechanisms")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public ContactMechanismListResponse contacts(@PathVariable final long partyId) {
        return partyContactMechanismService.findByParty(partyId);
    }
    

    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/e-addresses")
    @ApiOperation("Add an electronic address to a party")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public PartyElectronicAddressResponse addElectronicAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyElectronicAddressRequest request
    ) {
        return partyElectronicAddressService.addAddress(partyId, request);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
    @ApiOperation("Update an electronic address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Party or address not found", response = ErrorResponse.class)
    })
    public PartyElectronicAddressResponse updateElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId,
            @Valid @RequestBody PartyElectronicAddressRequest request
    ) {
        return partyElectronicAddressService.updateAddress(partyId, eaddressId, request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/e-addresses/{eaddressId}")
    @ApiOperation("Delete an electronic address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public void deleteElectronicAddress (
            @PathVariable final long partyId,
            @PathVariable final long eaddressId
    ) {
        partyElectronicAddressService.removeAddress(partyId, eaddressId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/p-addresses")
    @ApiOperation("Add an postal address to a party")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public PartyPostalAddressResponse addPostalAddress (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPostalAddressRequest request
    ) {
        return partyPostalAddressService.addAddress(partyId, request);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Update an postal address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Party or address not found", response = ErrorResponse.class)
    })
    public PartyPostalAddressResponse updatePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId,
            @Valid @RequestBody PartyPostalAddressRequest request
    ) {
        return partyPostalAddressService.updateAddress(partyId, paddressId, request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/p-addresses/{paddressId}")
    @ApiOperation("Delete an postal address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party or address not found", response = ErrorResponse.class)
    })
    public void deletePostalAddress (
            @PathVariable final long partyId,
            @PathVariable final long paddressId
    ) {
        partyPostalAddressService.removeAddress(partyId, paddressId);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/phones")
    @ApiOperation("Add an postal address to a party")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party not found", response = ErrorResponse.class)
    })
    public PartyPhoneResponse addPhone (
            @PathVariable final long partyId,
            @Valid @RequestBody CreatePartyPhoneRequest request
    ) {
        return partyPhoneService.addAddress(partyId, request);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Update an postal address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party or phone found", response = ErrorResponse.class)
    })
    public PartyPhoneResponse updatePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId,
            @Valid @RequestBody PartyPhoneRequest request
    ) {
        return partyPhoneService.updateAddress(partyId, phoneId, request);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{partyId}/contacts/phones/{phoneId}")
    @ApiOperation("Delete an postal address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Party or phone found", response = ErrorResponse.class)
    })
    public void deletePhone (
            @PathVariable final long partyId,
            @PathVariable final long phoneId
    ) {
        partyPhoneService.removeAddress(partyId, phoneId);
    }
}
