package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.domain.UserStatus;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import com.tchepannou.pdr.dto.user.CreateUserResponse;
import com.tchepannou.pdr.dto.user.GetUserResponse;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public GetUserResponse findById(@PathVariable final long id) {
        final User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(id);
        }

        return new GetUserResponse(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation("Create a new user account")
    public GetUserResponse create (CreateUserRequest request) {
        final User user = new User();
        user.setStatus(UserStatus.CREATED);
        user.setPartyId(request.getPartyId());
        user.setFromDate(LocalDateTime.now());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncryptor.encrypt(request.getPassword()));
        userService.create(user);

        return new CreateUserResponse(user);
    }
}
