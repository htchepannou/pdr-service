package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.Permission;
import com.tchepannou.pdr.domain.Role;
import com.tchepannou.pdr.dto.role.PermissionListResponse;
import com.tchepannou.pdr.dto.role.RoleListResponse;
import com.tchepannou.pdr.dto.role.RoleResponse;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PermissionService;
import com.tchepannou.pdr.service.RoleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(basePath = "/roles", value = "User's Roles and Permissions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {
    //-- Attributes
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation("Find all the roles")
    public RoleListResponse findAll() {
        final List<Role> all = roleService.findAll();
        return  new RoleListResponse.Builder()
                .withRoles(all)
                .build();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation("Find a role by ID")
    public RoleResponse findById(@PathVariable final long id) {
        final Role role = roleService.findById(id);
        if (role == null) {
            throw new NotFoundException(id);
        }

        return new RoleResponse.Builder()
                .withRole(role)
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}/permissions")
    @ApiOperation("Returns all the permissions of a given role")
    public PermissionListResponse findPermissions(@PathVariable final long id) {
        final List<Permission> all = permissionService.findByRole(id);
        return  new PermissionListResponse.Builder()
                .withPermissions(all)
                .build();
    }
}
