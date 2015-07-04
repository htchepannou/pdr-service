package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.auth.AuthRequest;
import com.tchepannou.pdr.dto.auth.AuthResponse;
import com.tchepannou.pdr.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Api(basePath = "/auth", value = "Authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController extends AbstractController {
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    //-- AbstractController overrides
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value="/{authId}")
    @ApiOperation("Find a token")
    public AuthResponse findById (@PathVariable final long authId) {
        AccessToken token = authService.findById(authId);
        return new AuthResponse.Builder()
                .withAccessToken(token)
                .build();
    }


    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Authenticate a user")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        AccessToken token = authService.login(request);
        return new AuthResponse.Builder()
                .withAccessToken(token)
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{authId}")
    @ApiOperation("Sign a user out")
    public void logout(@PathVariable final long authId) {
        authService.logout(authId);
    }


    //-- Private
    private AccessToken createAccessToken(AuthRequest request, User user) {
        AccessToken token = new AccessToken();
        token.setFromDate(new Date());
        token.setUserId(user.getId());
        token.setRemoteIp(request.getRemoteIp());
        token.setUserAgent(request.getUserAgent());
        token.setDomainId(request.getDomainId());

        return token;
    }
}
