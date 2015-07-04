package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.Domain;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.domain.Permission;
import com.tchepannou.pdr.domain.Role;
import com.tchepannou.pdr.dto.domain.DomainListResponse;
import com.tchepannou.pdr.dto.domain.DomainRequest;
import com.tchepannou.pdr.dto.domain.DomainResponse;
import com.tchepannou.pdr.dto.role.PermissionListResponse;
import com.tchepannou.pdr.dto.role.RoleListResponse;
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
@Api(basePath = "/domains", value = "Domains", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/domains", produces = MediaType.APPLICATION_JSON_VALUE)
public class DomainController extends AbstractController {
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    private DomainService domainService;

    @Autowired
    private DomainUserService domainUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //-- AbstractController overrides
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{domainId}")
    @ApiOperation("Find a domain by ID")
    public DomainResponse findById(@PathVariable final long domainId) {
        final Domain domain = domainService.findById(domainId);
        return new DomainResponse.Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("Find all the domains")
    public DomainListResponse findAll() {
        final List<Domain> all = domainService.findAll();
        return new DomainListResponse.Builder()
                .withDomains(all)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Create a new domain")
    public DomainResponse create(@RequestBody @Valid final DomainRequest request) {
        Domain domain = domainService.create(request);
        return new DomainResponse.Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{domainId}")
    @ApiOperation("Update a domain")
    public DomainResponse update(
            @PathVariable final long domainId,
            @RequestBody final DomainRequest request
    ) {
        Domain domain = domainService.update(domainId, request);
        return new DomainResponse.Builder()
                .withDomain(domain)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{domainId}")
    @ApiOperation("Delete a domain by ID")
    public void delete(@PathVariable final long domainId) {
        domainService.delete(domainId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{domainId}/users/{userId}/roles")
    @ApiOperation("Grant to a user a role in a domain")
    public RoleListResponse roles (
            @PathVariable final long domainId,
            @PathVariable final long userId
    ) {
        final List<DomainUser> domainUsers = domainUserService.findByDomainByUser(domainId, userId);
        final List<Role> roles = domainUsers.stream()
                .map(domainUser -> roleService.findById(domainUser.getRoleId()))
                .collect(Collectors.toList());

        return new RoleListResponse.Builder()
                .withRoles(roles)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{domainId}/users/{userId}/permissions")
    @ApiOperation("Grant to a user a role in a domain")
    public PermissionListResponse permissions (
            @PathVariable final long domainId,
            @PathVariable final long userId
    ) {
        final List<DomainUser> domainUsers = domainUserService.findByDomainByUser(domainId, userId);
        final List<Role> roles = domainUsers.stream()
                .map(domainUser -> roleService.findById(domainUser.getRoleId()))
                .collect(Collectors.toList());
        final List<Permission> permissions = roles.stream()
                .flatMap(role -> permissionService.findByRole(role.getId()).stream())
                .distinct()
                .collect(Collectors.toList());

        return new PermissionListResponse.Builder()
                .withPermissions(permissions)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{domainId}/users/{userId}/roles/{roleId}")
    @ApiOperation("Grant to a user a role in a domain")
    public void grant (
            @PathVariable final long domainId,
            @PathVariable final long userId,
            @PathVariable final long roleId
    ) {
        domainUserService.create(domainId, userId, roleId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{domainId}/users/{userId}/roles/{roleId}")
    @ApiOperation("Remove a user's role in a domain")
    public void revoke (
            @PathVariable final long domainId,
            @PathVariable final long userId,
            @PathVariable final long roleId
    ) {
        domainUserService.delete(domainId, userId, roleId);
    }
}
