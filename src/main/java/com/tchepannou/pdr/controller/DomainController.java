package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.dto.domain.DomainRequest;
import com.tchepannou.pdr.dto.domain.DomainResponse;
import com.tchepannou.pdr.service.DomainService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public DomainResponse findById(@PathVariable final long id) {
        final Domain domain = domainService.findById(id);
        return new DomainResponse
                .Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("Find all the domains")
    public List<DomainResponse> findAll() {
        final List<Domain> all = domainService.findAll();
        final DomainResponse.Builder builder = new DomainResponse.Builder();

        return all.stream()
                .map(d -> builder.withDomain(d).build())
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Create a new domain")
    public DomainResponse create(@RequestBody DomainRequest request) {
        final Domain domain = new Domain ();
        domain.setName(request.getName());
        domain.setDescription(request.getDescription());
        domainService.create(domain);

        return new DomainResponse
                .Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    @ApiOperation("Update a domain")
    public DomainResponse update(@PathVariable final long id, @RequestBody final DomainRequest request) {
        final Domain domain = domainService.findById(id);
        domain.setName(request.getName());
        domain.setDescription(request.getDescription());
        domainService.update(domain);

        return new DomainResponse
                .Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation("Delete a domain by ID")
    public void delete(@PathVariable final long id) {
        domainService.delete(id);
    }
}
