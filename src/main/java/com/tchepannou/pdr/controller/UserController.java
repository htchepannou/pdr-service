package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import com.tchepannou.pdr.dto.user.UserResponse;
import com.tchepannou.pdr.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(basePath = "/users", value = "User's Account", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    //-- Attributes
    @Autowired
    private UserService userService;


    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ApiOperation("Returns a User")
    public UserResponse findById(@PathVariable final long userId) {
        final User user = userService.findById(userId);
        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Create a new user account")
    public UserResponse create (@Valid @RequestBody final CreateUserRequest request) {
        final User user = userService.create(request);
        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userId}/password")
    @ApiOperation("Update a user's password")
    public UserResponse password (
            @PathVariable final long userId,
            @RequestParam(value = "password", required = true) @NotBlank final String password
    ) {
        User user = userService.updatePassword(userId, password);
        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userId}/login")
    @ApiOperation("Update a user's login")
    public UserResponse login (
            @PathVariable final long userId,
            @RequestParam(value = "login", required = true) @NotBlank final String login
    ) {
        User user = userService.updateLogin(userId, login);
        return new UserResponse.Builder()
                .withUser(user)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    @ApiOperation("Delete a user account")
    public void delete (@PathVariable final long userId) {
        userService.delete(userId);
    }
}
