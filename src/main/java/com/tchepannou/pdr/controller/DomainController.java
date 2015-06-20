package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.dto.DomainDto;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.DomainService;
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
@Api(basePath = "/domains", value = "Domain Manager", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/domains", produces = MediaType.APPLICATION_JSON_VALUE)
public class DomainController {
    //-- Attributes
    @Autowired
    private DomainService domainService;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation("Find a domain by ID")
    public DomainDto findById(@PathVariable final long id) {
        final Domain domain = domainService.findById(id);
        if (domain == null) {
            throw new NotFoundException(id);
        }

        return new DomainDto
                .Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("Find all the domains")
    public List<DomainDto> findAll() {
        final List<Domain> all = domainService.findAll();
        final DomainDto.Builder builder = new DomainDto.Builder();

        return all.stream()
                .map(d -> builder.withDomain(d).build())
                .collect(Collectors.toList());
    }
}
