package com.tchepannou.pdr.controller;

import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.auth.AuthRequest;
import com.tchepannou.pdr.dto.auth.AuthResponse;
import com.tchepannou.pdr.exception.AccessDeniedException;
import com.tchepannou.pdr.exception.AccessTokenExpiredException;
import com.tchepannou.pdr.exception.AuthFailedException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AccessTokenService;
import com.tchepannou.pdr.service.DomainUserService;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Api(basePath = "/auth", value = "Authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    //-- Attributes
    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private DomainUserService domainUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncryptor passwordEncryptor;


    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value="/{authId}")
    @ApiOperation("Find a token")
    public AuthResponse findById (@PathVariable final long authId) {
        AccessToken token = accessTokenService.findById(authId);
        if (token.isExpired()){
            throw new AccessTokenExpiredException(authId);
        }
        return new AuthResponse.Builder()
                .withAccessToken(token)
                .build();
    }


    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation("Authenticate a user")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        try {
            /* Authenticate */
            User user = userService.findByLogin(request.getLogin());
            if (!passwordEncryptor.matches(request.getPassword(), user)){
                throw new AuthFailedException();
            }

            /* Make sure user as access to domain */
            List<DomainUser> domainUsers = domainUserService.findByDomainByUser(request.getDomainId(), user.getId());
            if (domainUsers.isEmpty()){
                throw new AccessDeniedException();
            }

            /* Create access token */
            AccessToken token = createAccessToken(request, user);
            accessTokenService.create(token);

            return new AuthResponse.Builder()
                    .withAccessToken(token)
                    .build();
        } catch (NotFoundException e) {
            throw new AuthFailedException(e);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{authId}")
    @ApiOperation("Sign a user out")
    public void logout(@PathVariable final long authId) {
        AccessToken token = accessTokenService.findById(authId);
        if (token.isExpired()){
            throw new AccessTokenExpiredException(authId);
        }
        token.expire();
        accessTokenService.update(token);
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
