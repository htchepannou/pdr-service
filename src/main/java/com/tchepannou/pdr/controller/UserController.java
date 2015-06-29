package com.tchepannou.pdr.controller;

import com.google.common.base.Strings;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.domain.UserStatus;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import com.tchepannou.pdr.dto.user.UpdateUserRequest;
import com.tchepannou.pdr.dto.user.UserResponse;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Api(basePath = "/users", value = "Manages user's account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    //-- Attributes
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiOperation("Returns a User")
    public UserResponse findById(@PathVariable final long id) {
        final User user = userService.findById(id);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException(id);
        }

        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Create a new user account")
    public UserResponse create (@RequestBody final CreateUserRequest request) {
        final User user = new User();
        user.setStatus(UserStatus.CREATED);
        user.setPartyId(request.getPartyId());
        user.setFromDate(LocalDateTime.now());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncryptor.encrypt(request.getPassword()));
        userService.create(user);

        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    @ApiOperation("Update a user's account")
    public UserResponse update (@PathVariable final long id, @RequestBody final UpdateUserRequest request) {
        final User user = userService.findById(id);

        if (!Strings.isNullOrEmpty(request.getStatus())) {
            user.setStatus(UserStatus.fromText(request.getStatus()));
        }

        if (!Strings.isNullOrEmpty(request.getLogin())) {
            user.setLogin(request.getLogin());
        }

        if (!Strings.isNullOrEmpty(request.getPassword())) {
            user.setPassword(request.getPassword());
        }

        userService.update(user);

        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiOperation("Delete a user account")
    public void delete (@PathVariable final long id) {
        userService.delete(id);
    }
}
