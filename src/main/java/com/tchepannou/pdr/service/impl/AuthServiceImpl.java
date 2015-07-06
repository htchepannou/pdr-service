package com.tchepannou.pdr.service.impl;

import com.tchepannou.pdr.dao.AccessTokenDao;
import com.tchepannou.pdr.domain.AccessToken;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.dto.auth.AuthRequest;
import com.tchepannou.pdr.exception.AccessDeniedException;
import com.tchepannou.pdr.exception.AccessTokenExpiredException;
import com.tchepannou.pdr.exception.AuthFailedException;
import com.tchepannou.pdr.exception.NotFoundException;
import com.tchepannou.pdr.service.AuthService;
import com.tchepannou.pdr.service.DomainUserService;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class AuthServiceImpl implements AuthService {
    //-- Attributes
    @Value("${access_token.ttl.minutes:30}")
    private int ttl;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private DomainUserService domainUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    //-- AuthService overrides
    @Override
    public AccessToken findById(final long id) {
        AccessToken token = accessTokenDao.findById(id);
        if (token == null) {
            throw new NotFoundException(id, AccessToken.class);
        } else if (token.isExpired()){
            throw new AccessTokenExpiredException(id);
        }
        return token;
    }

    @Override
    @Transactional
    public AccessToken login(final AuthRequest request) {
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
            accessTokenDao.create(token);

            return token;
        } catch (NotFoundException e) {
            throw new AuthFailedException(e);
        }
    }

    @Override
    @Transactional
    public void logout(final long authId) {
        AccessToken token = findById(authId);
        token.expire();
        accessTokenDao.update(token);
    }

    //-- Private
    private AccessToken createAccessToken(AuthRequest request, User user) {
        final Date now = new Date();
        final AccessToken token = new AccessToken();
        token.setFromDate(now);
        token.setUserId(user.getId());
        token.setRemoteIp(request.getRemoteIp());
        token.setUserAgent(request.getUserAgent());
        token.setDomainId(request.getDomainId());
        token.setExpiryDate(DateUtils.addMinutes(now, ttl));

        return token;
    }

}
