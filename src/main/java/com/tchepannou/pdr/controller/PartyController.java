package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.dto.PartyDto;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PartyService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(basePath = "/parties", value = "Manages People, Organization and Household", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/parties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartyController {
    //-- Attributes
    @Autowired
    private PartyService partyService;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation("Find a party by ID")
    public PartyDto findById(@PathVariable final long id) {
        final Party domain = partyService.findById(id);
        if (domain == null) {
            throw new NotFoundException(id);
        }

        return new PartyDto
                .Builder()
                .withParty(domain)
                .build();
    }
}
